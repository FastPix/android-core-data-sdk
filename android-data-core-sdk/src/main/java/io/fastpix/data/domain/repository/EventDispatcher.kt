package io.fastpix.data.domain.repository

import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.domain.model.events.BaseEvent
import io.fastpix.data.pipeline.EventQueue
import io.fastpix.data.utils.Logger
import io.fastpix.data.utils.NetworkTracker
import io.fastpix.data.work.EventUploadScheduler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Dedicated analytics worker and event pipeline. All analytics operations run on a single
 * [analyticsScope] (SupervisorJob + Dispatchers.IO) for ordered processing and thread safety.
 *
 * Pipeline: Player Event → Event Queue (memory) → Room persistence → WorkManager upload.
 *
 * Events are only accepted via [enqueue] (exposed as [dispatchEvent] for API compatibility).
 * Player adapters must not send to the network directly; they push to this dispatcher only.
 */
class EventDispatcher(
    private val eventApiService: EventApiService,
    private val networkTracker: NetworkTracker,
    private val context: android.content.Context
) {
    companion object {
        private const val DRAIN_CHUNK_SIZE = 100
    }

    private val analyticsJob = SupervisorJob()
    private val analyticsScope = CoroutineScope(analyticsJob + Dispatchers.IO)
    private val eventQueue = EventQueue(EventQueue.DEFAULT_CAPACITY)

    private val isNetworkAvailable = AtomicBoolean(false)
    private val isShuttingDown = AtomicBoolean(false)

    private var pipelineJob: Job? = null

    init {
        startNetworkMonitoring()
        startPipeline()
    }

    /**
     * Enqueue an event. All events (including viewBegin/playerReady) go through this path.
     * Does nothing if dispatcher is shutting down.
     */
    fun enqueue(event: BaseEvent): Boolean {
        if (isShuttingDown.get()) {
            Logger.logWarning("EventDispatcher", "EVENT_ENQUEUE_SKIPPED: dispatcher shutting down")
            return false
        }
        if (!eventQueue.enqueue(event)) {
            Logger.logWarning("EventDispatcher", "EVENT_ENQUEUE_SKIPPED: queue closed event=${event.eventName}")
            return false
        }
        Logger.log(
            "EventDispatcher",
            "${Logger.EVENT_ENQUEUED}: ${event.eventName} queueSize=${eventQueue.size}"
        )
        return true
    }

    /**
     * Same as [enqueue]. Kept for API compatibility with existing SDK code.
     */
    fun dispatchEvent(eventData: BaseEvent): Boolean {
        return enqueue(eventData)
    }

    private fun startNetworkMonitoring() {
        analyticsScope.launch {
            networkTracker.isNetworkAvailable.collect { available ->
                val was = isNetworkAvailable.get()
                isNetworkAvailable.set(available)
                if (!was && available) {
                    Logger.log("EventDispatcher", "Network restored, scheduling upload worker")
                    EventUploadScheduler.schedule(context)
                } else if (was && !available) {
                    Logger.logWarning("EventDispatcher", "Network lost, events will stay in Room")
                }
            }
        }
    }

    private fun startPipeline() {
        pipelineJob = analyticsScope.launch {
            while (isActive && !isShuttingDown.get()) {
                try {
                    val eventStore = DependencyContainer.getEventStore()

                    val drained = mutableListOf<BaseEvent>()
                    eventQueue.drainTo(drained, DRAIN_CHUNK_SIZE)
                    if (drained.isNotEmpty()) {
                        drained.forEach { eventStore.onNewEvent(it) }
                        Logger.log("EventDispatcher", "${Logger.EVENT_BATCHED}: ${drained.size} events")
                        EventUploadScheduler.schedule(context)
                    }

                    delay(500)
                } catch (e: CancellationException) {
                    break
                } catch (e: Exception) {
                    Logger.logError("EventDispatcher", "Pipeline error", e)
                    delay(1000)
                }
            }
        }
    }

    /**
     * Flush in-memory queue, persist remaining events, send final batches, then shut down.
     * Blocks until shutdown is complete. Must not be called concurrently with [enqueue].
     */
    fun flushAndShutdown() {
        if (isShuttingDown.getAndSet(true)) return
        Logger.log("EventDispatcher", "${Logger.SDK_RELEASE_STARTED}: flushing and shutting down")
        runBlocking(Dispatchers.IO) {
            eventQueue.close()
            val remaining = mutableListOf<BaseEvent>()
            eventQueue.drainAllTo(remaining)
            val eventStore = DependencyContainer.getEventStore()
            remaining.forEach { eventStore.onNewEvent(it) }
            eventStore.markActiveSessionsCompleted()
            EventUploadScheduler.schedule(context)
            pipelineJob?.cancel()
            analyticsJob.cancel()
        }
        Logger.log("EventDispatcher", "${Logger.SDK_RELEASE_COMPLETED}")
    }

    /**
     * Send persisted events (used by CleanupWorker after process restart).
     */
    suspend fun sendPersistedEvents(events: List<BaseEvent>): Pair<Boolean, List<BaseEvent>> {
        return Pair(true, events)
    }

    fun cleanThroughWorkManager(onCleanUpDone: (() -> Unit)? = null) {
        try {
            EventUploadScheduler.schedule(context)
            Logger.log("EventDispatcher", "Event upload worker scheduled")
        } catch (e: Exception) {
            Logger.logError("EventDispatcher", "Failed to schedule upload via WorkManager", e)
        }
        onCleanUpDone?.invoke()
    }

    suspend fun onSdkInitialized() {
        DependencyContainer.getEventStore().onSdkInitialized()
        EventUploadScheduler.schedule(context)
    }
}
