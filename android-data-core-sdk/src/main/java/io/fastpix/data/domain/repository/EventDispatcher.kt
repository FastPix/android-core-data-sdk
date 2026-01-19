package io.fastpix.data.domain.repository

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.domain.enums.PlayerEventType
import io.fastpix.data.domain.model.EventRequest
import io.fastpix.data.domain.model.Metadata
import io.fastpix.data.domain.model.events.BaseEvent
import io.fastpix.data.domain.model.events.PulseEvent
import io.fastpix.data.domain.model.events.PulseEventBuilder
import io.fastpix.data.utils.Logger
import io.fastpix.data.utils.NetworkTracker
import io.fastpix.data.work.CleanupWorker
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class EventDispatcher(
    private val eventApiService: EventApiService,
    private val networkTracker: NetworkTracker,
    private val context: Context
) {

    companion object {
        private const val PRIMARY_QUEUE_SIZE = 100
        private const val BATCH_DISPATCH_INTERVAL = 10_000L // 10 seconds
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val INITIAL_RETRY_DELAY = 1_000L // 1 second
        private const val MAX_RETRY_DELAY = 30_000L // 30 seconds
    }

    // State management
    private val isDispatching = AtomicBoolean(false)
    private val isNetworkAvailable = AtomicBoolean(false)

    private val retryQueue = ConcurrentLinkedQueue<BaseEvent>()
    private val retryAttempts = AtomicInteger(0)

    // Coroutine management
    private val dispatcherScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var batchDispatchJob: Job? = null
    private var networkMonitorJob: Job? = null

    init {
        startNetworkMonitoring()
        startBatchDispatching()
    }

    /**
     * Add an event to the appropriate queue
     */
    fun dispatchEvent(eventData: BaseEvent) {
        if (eventData.eventName != "requestCompleted" && eventData.eventName != "requestFailed" && eventData.eventName != "requestCanceled") {
            Log.e("EventDispatcher", "Added Event: ${eventData.eventName}")
        }

        when (eventData.eventName) {
            PlayerEventType.viewBegin.name, PlayerEventType.playerReady.name -> {
                dispatchImmediately(eventData)
            }

            else -> {
                addToQueue(eventData)
            }
        }
    }

    /**
     * Dispatch viewBegin and playerReady events immediately
     * Note: Removed NonCancellable wrapper to allow proper timeout handling and prevent blocking
     */
    private fun dispatchImmediately(eventData: BaseEvent) {
        if (!isNetworkAvailable.get()) {
            Logger.logWarning(
                "EventDispatcher",
                "Network unavailable, adding ${eventData.eventName} to retry queue"
            )
            retryQueue.offer(eventData)
            return
        }
        dispatcherScope.launch {
            try {
                val eventRequest = createEventRequest(listOf(eventData))
                val response = eventApiService.sendEvents(eventRequest)
                if (!response.isSuccessful) {
                    Logger.logError(
                        "EventDispatcher",
                        "Failed to dispatch ${eventData.eventName} event: ${response.code()}"
                    )
                    retryQueue.offer(eventData)
                }
            } catch (e: CancellationException) {
                // Request was cancelled - add to retry queue so it can be sent later
                Logger.logWarning(
                    "EventDispatcher",
                    "${eventData.eventName} event cancelled, adding to retry queue"
                )
                retryQueue.offer(eventData)
            } catch (e: Exception) {
                Logger.logError(
                    "EventDispatcher",
                    "Exception dispatching ${eventData.eventName} event",
                    e
                )
                retryQueue.offer(eventData)
            }
        }
    }

    /**
     * Add event to appropriate queue
     */
    private fun addToQueue(event: BaseEvent) {
        DependencyContainer.getEventPersistenceManager()
            .savePendingEvents(listOf(event))
    }

    /**
     * Start network monitoring
     */
    private fun startNetworkMonitoring() {
        networkMonitorJob = dispatcherScope.launch {
            networkTracker.isNetworkAvailable.collect { isAvailable ->
                val wasAvailable = isNetworkAvailable.get()
                isNetworkAvailable.set(isAvailable)

                if (!wasAvailable && isAvailable) {
                    processRetryQueue()
                    Logger.log("EventDispatcher", "Network restored, resuming dispatch")
                } else if (wasAvailable && !isAvailable) {
                    Logger.logWarning("EventDispatcher", "Network lost, pausing dispatch")
                }
            }
        }
    }

    /**
     * Start batch dispatching
     */
    private fun startBatchDispatching() {
        batchDispatchJob = dispatcherScope.launch {
            while (isActive) {
                delay(BATCH_DISPATCH_INTERVAL)

                if (isNetworkAvailable.get() && !isDispatching.get()) {
                    processQueues()
                }
            }
        }
    }

    /**
     * Process all queues in priority order
     * Uses NonCancellable to ensure ongoing dispatches complete even if scope is cancelled
     */
    private suspend fun processQueues() {
        if (isDispatching.get()) return
        isDispatching.set(true)
        try {
            // Use NonCancellable to ensure we complete processing even if scope is cancelled
            // This is critical when switching videos - we don't want to lose events
            withContext(NonCancellable) {
                // First process retry queue
                processRetryQueue()

                // Finally process primary queue
                processPrimaryQueue()
            }
        } finally {
            isDispatching.set(false)
        }
    }

    private suspend fun processRetryQueue() {
        if (retryQueue.isEmpty()) return

        val eventsToRetry = mutableListOf<BaseEvent>()
        repeat(minOf(retryQueue.size, PRIMARY_QUEUE_SIZE)) {
            retryQueue.poll()?.let { eventsToRetry.add(it) }
        }

        if (eventsToRetry.isNotEmpty()) {
            val (success, successfullySentEvents) = dispatchBatch(eventsToRetry)
            if (!success) {
                val currentAttempts = retryAttempts.incrementAndGet()
                if (currentAttempts <= MAX_RETRY_ATTEMPTS) {
                    val delay = minOf(
                        INITIAL_RETRY_DELAY * (1L shl (currentAttempts - 1)),
                        MAX_RETRY_DELAY
                    )

                    dispatcherScope.launch {
                        delay(delay)
                        eventsToRetry.forEach { retryQueue.offer(it) }
                    }
                } else {
                    Logger.logError(
                        "EventDispatcher",
                        "Max retry attempts reached, dropping events"
                    )
                }
            } else {
                // Delete successfully sent events from persistence by viewerTimeStamp
                if (successfullySentEvents.isNotEmpty()) {
                    val viewerTimeStamps =
                        successfullySentEvents.mapNotNull { it.viewerTimeStamp }.toSet()
                    if (viewerTimeStamps.isNotEmpty()) {
                        DependencyContainer.getEventPersistenceManager()
                            .deleteEventsByViewerTimeStamps(viewerTimeStamps)
                    }
                }
                retryAttempts.set(0)
            }
        }
    }

    /**
     * Process primary queue
     * CRITICAL: Loads events, immediately deletes them from persistence, then sends them.
     * If send fails, events are added back to persistence to prevent data loss.
     * This prevents race conditions where multiple calls load the same events.
     */
    private suspend fun processPrimaryQueue() {
        val persistenceManager = DependencyContainer.getEventPersistenceManager()
        val eventsToDispatch = persistenceManager.loadPendingEvents()

        if (eventsToDispatch.isEmpty()) return

        // Group by viewId for batching
        val batchByVeid = eventsToDispatch.groupBy { it.viewId }

        // Process each batch
        batchByVeid.values.forEach { events ->
            if (events.isNotEmpty()) {
                // CRITICAL FIX: Delete events from persistence BEFORE sending
                // This prevents race conditions where multiple calls load the same events
                val viewerTimeStamps = events.mapNotNull { it.viewerTimeStamp }.toSet()
                if (viewerTimeStamps.isNotEmpty()) {
                    persistenceManager.deleteEventsByViewerTimeStamps(viewerTimeStamps)
                }

                // Now send the events
                val (success, successfullySentEvents) = dispatchBatch(events)

                if (!success) {
                    // Send failed - add events back to persistence for retry
                    Logger.logWarning(
                        "EventDispatcher",
                        "Failed to send ${events.size} events, adding back to persistence for retry"
                    )
                    persistenceManager.savePendingEvents(events)
                } else {
                    // Send succeeded - events already deleted, nothing to do
                    Logger.log(
                        "EventDispatcher",
                        "Successfully sent ${successfullySentEvents.size} events"
                    )
                }
            }
        }
    }

    /**
     * Create EventRequest with metadata and events
     */
    private fun createEventRequest(events: List<BaseEvent>): EventRequest {
        val currentTimestamp = System.currentTimeMillis()
        val metadata = Metadata(transmission_timestamp = currentTimestamp)
        return EventRequest(metadata = metadata, events = events)
    }

    /**
     * Dispatch a batch of events
     * Uses NonCancellable context to ensure HTTP requests complete even if scope is cancelled
     *
     * @return Pair of (success: Boolean, successfullySentEvents: List<BaseEvent>)
     *         If successful, returns the list of events that were successfully sent
     */
    private suspend fun dispatchBatch(events: List<BaseEvent>): Pair<Boolean, List<BaseEvent>> {
        if (events.isEmpty()) return Pair(true, emptyList())

        return try {
            // Use NonCancellable to ensure the HTTP request completes even if scope is cancelled
            // This is critical when switching videos - we don't want to lose events
            withContext(NonCancellable) {
                val eventRequest = createEventRequest(events)
                val response = eventApiService.sendEvents(eventRequest)

                if (response.isSuccessful) {
                    Pair(true, events)
                } else {
                    Pair(false, emptyList())
                }
            }
        } catch (e: CancellationException) {
            // This should rarely happen now due to NonCancellable, but handle gracefully
            Logger.logWarning("EventDispatcher", "Batch dispatch cancelled, events will be retried")
            e.printStackTrace()
            Pair(false, emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Logger.logError("EventDispatcher", "Exception during batch dispatch", e)
            Pair(false, emptyList())
        }
    }

    /**
     * Send persisted events (used by CleanupWorker after app restart)
     * This method sends events that were saved to persistent storage before app termination
     * CRITICAL: Deletes events from persistence BEFORE sending to prevent duplicates.
     * If send fails, events are added back to persistence for retry.
     *
     * @return Pair of (allSuccess: Boolean, successfullySentEvents: List<BaseEvent>)
     */
    suspend fun sendPersistedEvents(events: List<BaseEvent>): Pair<Boolean, List<BaseEvent>> {
        if (events.isEmpty()) return Pair(true, emptyList())

        val persistenceManager = DependencyContainer.getEventPersistenceManager()

        // CRITICAL FIX: Delete events from persistence BEFORE sending
        // This prevents race conditions where multiple calls load the same events
        val viewerTimeStampsToDelete = events.mapNotNull { it.viewerTimeStamp }.toSet()
        if (viewerTimeStampsToDelete.isNotEmpty()) {
            persistenceManager.deleteEventsByViewerTimeStamps(viewerTimeStampsToDelete)
        }

        // Send events in batches
        val batchSize = PRIMARY_QUEUE_SIZE
        val batches = events.chunked(batchSize)
        var allSuccess = true
        val allSuccessfullySentEvents = mutableListOf<BaseEvent>()
        val failedBatches = mutableListOf<List<BaseEvent>>()

        for (batch in batches) {
            try {
                val (success, successfullySentEvents) = dispatchBatch(batch)
                if (!success) {
                    allSuccess = false
                    failedBatches.add(batch)
                } else {
                    allSuccessfullySentEvents.addAll(successfullySentEvents)
                }
            } catch (e: Exception) {
                allSuccess = false
                failedBatches.add(batch)
                Logger.logError(
                    "EventDispatcher",
                    "Exception sending persisted events batch, will retry",
                    e
                )
            }
        }

        // If any batches failed, add them back to persistence for retry
        if (failedBatches.isNotEmpty()) {
            val failedEvents = failedBatches.flatten()
            Logger.logWarning(
                "EventDispatcher",
                "Failed to send ${failedEvents.size} persisted events, adding back to persistence for retry"
            )
            persistenceManager.savePendingEvents(failedEvents)
        }

        return Pair(allSuccess, allSuccessfullySentEvents)
    }

    fun cleanThroughWorkManager(onCleanUpDone: (() -> Unit)? = null) {
        val appContext = context ?: DependencyContainer.getContext()
        try {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val cleanupWorkRequest = OneTimeWorkRequestBuilder<CleanupWorker>()
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(appContext)
                .enqueue(cleanupWorkRequest)
            Logger.log("EventDispatcher", "Cleanup WorkManager scheduled")
        } catch (e: Exception) {
            Logger.logError("EventDispatcher", "Failed to schedule cleanup via WorkManager", e)
            onCleanUpDone?.invoke()
        }
    }
}
