package io.fastpix.data.domain.repository

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.domain.enums.PlayerEventType
import io.fastpix.data.domain.model.EventRequest
import io.fastpix.data.domain.model.Metadata
import io.fastpix.data.domain.model.events.PulseEvent
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
        private const val PULSE_INTERVAL = 10_000L // 1 second
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val INITIAL_RETRY_DELAY = 1_000L // 1 second
        private const val MAX_RETRY_DELAY = 30_000L // 30 seconds
    }

    // Dual queue system
    private val primaryQueue = ConcurrentLinkedQueue<Map<String, String?>>()
    private val overflowQueue = ConcurrentLinkedQueue<Map<String, String?>>()
    private val retryQueue = ConcurrentLinkedQueue<Map<String, String?>>()

    // State management
    private val isDispatching = AtomicBoolean(false)
    private val isNetworkAvailable = AtomicBoolean(false)
    private val isPulseScheduled = AtomicBoolean(false)
    private val retryAttempts = AtomicInteger(0)
    private val isPendingFlush = AtomicBoolean(false)

    // Coroutine management
    private val dispatcherScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var batchDispatchJob: Job? = null
    private var pulseJob: Job? = null
    private var networkMonitorJob: Job? = null

    // Pending flush callback storage
    private var pendingFlushCallback: (() -> Unit)? = null

    // Pulse event tracking
    private var lastPlayingEvent: Map<String, String?>? = null
    private var pulseSequenceNumber = 0

    init {
        startNetworkMonitoring()
        startBatchDispatching()
    }

    /**
     * Add an event to the appropriate queue
     */
    fun dispatchEvent(eventData: Map<String, String?>) {
        Logger.log("EventDispatcher", "Adding event: ${eventData["evna"]}")

        when (eventData["evna"]) {
            PlayerEventType.viewBegin.name, PlayerEventType.playerReady.name -> {
                dispatchImmediately(eventData)
            }

            PlayerEventType.playing.name -> {
                handlePlayingEvent(eventData)
            }

            else -> {
                cancelPulseEvent()
                addToQueue(eventData)
            }
        }
    }

    /**
     * Dispatch viewBegin events immediately
     */
    private fun dispatchImmediately(eventData: Map<String, String?>) {
        if (!isNetworkAvailable.get()) {
            Logger.logWarning(
                "EventDispatcher",
                "Network unavailable, adding viewBegin to retry queue"
            )
            retryQueue.offer(eventData)
            return
        }

        dispatcherScope.launch {
            try {
                val eventRequest = createEventRequest(listOf(eventData))
                val response = eventApiService.sendEvents(eventRequest)
                if (response.isSuccessful) {
                    Logger.log("EventDispatcher", "viewBegin event dispatched successfully")
                } else {
                    Logger.logError(
                        "EventDispatcher",
                        "Failed to dispatch viewBegin event: ${response.code()}"
                    )
                    retryQueue.offer(eventData)
                }
            } catch (e: Exception) {
                Logger.logError("EventDispatcher", "Exception dispatching viewBegin event", e)
                retryQueue.offer(eventData)
            }
        }
    }

    /**
     * Handle playing events with pulse logic
     */
    private fun handlePlayingEvent(event: Map<String, String?>) {
        if (lastPlayingEvent == null) {
            // First playing event - dispatch immediately and start pulse
            lastPlayingEvent = event
            addToQueue(event)
            schedulePulseEvents()
        } else {
            // Subsequent playing event - replace with pulse
            Logger.log("EventDispatcher", "Replacing playing event with pulse")
            // Don't add to queue, pulse will handle it
        }
    }

    /**
     * Schedule pulse events every second
     */
    private fun schedulePulseEvents() {
        if (isPulseScheduled.get()) return

        isPulseScheduled.set(true)
        pulseJob = dispatcherScope.launch {
            while (isPulseScheduled.get() && lastPlayingEvent != null) {
                delay(PULSE_INTERVAL)
                if (isPulseScheduled.get() && lastPlayingEvent != null) {
                    withContext(Dispatchers.Main) {
                        val pulseEvent = createPulseEvent()
                        addToQueue(pulseEvent)
                        pulseSequenceNumber++
                    }
                }
            }
        }
    }

    /**
     * Cancel pulse events
     */
    private fun cancelPulseEvent() {
        if (isPulseScheduled.get()) {
            Logger.log("EventDispatcher", "Cancelling pulse events")
            isPulseScheduled.set(false)
            pulseJob?.cancel()
            lastPlayingEvent = null
            pulseSequenceNumber = 0
        }
    }

    /**
     * Create a pulse event based on the last playing event
     */
    private fun createPulseEvent(): Map<String, String?> {
        val sdkStateService = DependencyContainer.getSDKStateService()
        val pulseEvent =
            PulseEvent.createPulseEvent(sdkStateService.sdkState.value.sdkConfiguration!!)
        return pulseEvent.toJson()
    }

    /**
     * Add event to appropriate queue
     */
    private fun addToQueue(event: Map<String, String?>) {
        if (primaryQueue.size < PRIMARY_QUEUE_SIZE) {
            primaryQueue.offer(event)
            val data = mutableListOf<Map<String, String?>>()
            primaryQueue.forEach { events ->
                data.add(events)
            }
            DependencyContainer.getEventPersistenceManager()
                .savePendingEvents(data, synchronous = true)
            Logger.log("EventDispatcher", "Added to primary queue. Size: ${primaryQueue.size}")
        } else {
            overflowQueue.offer(event)
            Logger.log("EventDispatcher", "Added to overflow queue. Size: ${overflowQueue.size}")
        }
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
                    Logger.log("EventDispatcher", "Network restored, resuming dispatch")

                    // Check if there's a pending flush waiting for network
                    if (isPendingFlush.get()) {
                        Logger.log("EventDispatcher", "Network restored, executing pending flush")
                        performFlush(null)
                    } else {
                        // Network came back, try to dispatch retry queue first
                        processRetryQueue()
                    }
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
     */
    private suspend fun processQueues() {
        if (isDispatching.get()) return

        isDispatching.set(true)

        try {
            // First process retry queue
            processRetryQueue()

            // Then process overflow queue
            processOverflowQueue()

            // Finally process primary queue
            processPrimaryQueue()

        } finally {
            isDispatching.set(false)
        }
    }

    /**
     * Process retry queue with exponential backoff
     */
    private suspend fun processRetryQueue() {
        if (retryQueue.isEmpty()) return

        Logger.log("EventDispatcher", "Processing retry queue. Size: ${retryQueue.size}")

        val eventsToRetry = mutableListOf<Map<String, String?>>()
        repeat(minOf(retryQueue.size, PRIMARY_QUEUE_SIZE)) {
            retryQueue.poll()?.let { eventsToRetry.add(it) }
        }

        if (eventsToRetry.isNotEmpty()) {
            val success = dispatchBatch(eventsToRetry)

            if (!success) {
                // Retry failed, add back to retry queue with exponential backoff
                val currentAttempts = retryAttempts.incrementAndGet()
                if (currentAttempts <= MAX_RETRY_ATTEMPTS) {
                    val delay = minOf(
                        INITIAL_RETRY_DELAY * (1L shl (currentAttempts - 1)),
                        MAX_RETRY_DELAY
                    )

                    Logger.logWarning(
                        "EventDispatcher",
                        "Retry failed, scheduling retry in ${delay}ms"
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
                retryAttempts.set(0) // Reset on success
            }
        }
    }

    /**
     * Process overflow queue
     */
    private suspend fun processOverflowQueue() {
        if (overflowQueue.isEmpty()) return

        Logger.log("EventDispatcher", "Processing overflow queue. Size: ${overflowQueue.size}")

        val eventsToDispatch = mutableListOf<Map<String, String?>>()
        repeat(minOf(overflowQueue.size, PRIMARY_QUEUE_SIZE)) {
            overflowQueue.poll()?.let { eventsToDispatch.add(it) }
        }

        if (eventsToDispatch.isNotEmpty()) {
            val success = dispatchBatch(eventsToDispatch)
            if (!success) {
                // Move failed events to retry queue
                eventsToDispatch.forEach { retryQueue.offer(it) }
            }
        }
    }

    /**
     * Process primary queue
     */
    private suspend fun processPrimaryQueue() {
        if (primaryQueue.isEmpty()) return

        Logger.log("EventDispatcher", "Processing primary queue. Size: ${primaryQueue.size}")

        val eventsToDispatch = mutableListOf<Map<String, String?>>()
        repeat(primaryQueue.size) {
            primaryQueue.poll()?.let { eventsToDispatch.add(it) }
        }

        if (eventsToDispatch.isNotEmpty()) {
            DependencyContainer.getEventPersistenceManager().clearPendingEvents()
            val success = dispatchBatch(eventsToDispatch)
            if (!success) {
                // Move failed events to retry queue
                eventsToDispatch.forEach { retryQueue.offer(it) }
            }
        }
    }

    /**
     * Create EventRequest with metadata and events
     */
    private fun createEventRequest(events: List<Map<String, String?>>): EventRequest {
        val currentTimestamp = System.currentTimeMillis().toString()
        val metadata = Metadata(transmission_timestamp = currentTimestamp)
        return EventRequest(metadata = metadata, events = events)
    }

    /**
     * Dispatch a batch of events
     */
    private suspend fun dispatchBatch(events: List<Map<String, String?>>): Boolean {
        if (events.isEmpty()) return true

        return try {
            Logger.log("EventDispatcher", "Dispatching batch of ${events.size} events")
            val eventRequest = createEventRequest(events)
            val response = eventApiService.sendEvents(eventRequest)

            if (response.isSuccessful) {
                Logger.log("EventDispatcher", "Batch dispatched successfully")
                true
            } else {
                Logger.logError("EventDispatcher", "Batch dispatch failed: ${response.code()}")
                false
            }
        } catch (e: CancellationException) {
            // Handle cancellation gracefully - this is expected during cleanup
            Logger.log("EventDispatcher", "Batch dispatch cancelled during cleanup")
            false
        } catch (e: Exception) {
            Logger.logError("EventDispatcher", "Exception during batch dispatch", e)
            false
        }
    }

    /**
     * Send persisted events (used by CleanupWorker after app restart)
     * This method sends events that were saved to persistent storage before app termination
     */
    suspend fun sendPersistedEvents(events: List<Map<String, String?>>): Boolean {
        if (events.isEmpty()) return true

        Logger.log("EventDispatcher", "Sending ${events.size} persisted events")

        // Send events in batches
        val batchSize = PRIMARY_QUEUE_SIZE
        val batches = events.chunked(batchSize)
        var allSuccess = true

        for (batch in batches) {
            val success = dispatchBatch(batch)
            if (!success) {
                allSuccess = false
                Logger.logWarning(
                    "EventDispatcher",
                    "Failed to send batch of ${batch.size} persisted events"
                )
            }
        }

        return allSuccess
    }

    /**
     * Flush all remaining events to the server before cleanup
     * If network is unavailable, will wait for network to come back before flushing
     */
    suspend fun flushAllEvents(onFlushDone: (() -> Unit)?) {
        Logger.log("EventDispatcher", "Flushing all remaining events before cleanup")

        // Check if network is available
        if (!isNetworkAvailable.get()) {
            Logger.logWarning(
                "EventDispatcher",
                "Network unavailable during flush, waiting for network to resume..."
            )
            isPendingFlush.set(true)
            // The flush will be executed when network comes back in startNetworkMonitoring()
            // Store the callback for later execution
            pendingFlushCallback = onFlushDone
            return
        }

        // Network is available, proceed with flush
        performFlush(onFlushDone)
    }

    /**
     * Internal method to perform the actual flush operation
     */
    private suspend fun performFlush(onFlushDone: (() -> Unit)?) {
        // Cancel pulse events first
        cancelPulseEvent()

        // Collect all events from all queues
        val allEvents = mutableListOf<Map<String, String?>>()

        // Add events from retry queue (highest priority)
        while (retryQueue.isNotEmpty()) {
            retryQueue.poll()?.let { allEvents.add(it) }
        }

        // Add events from overflow queue
        while (overflowQueue.isNotEmpty()) {
            overflowQueue.poll()?.let { allEvents.add(it) }
        }

        // Add events from primary queue
        while (primaryQueue.isNotEmpty()) {
            primaryQueue.poll()?.let { allEvents.add(it) }
        }

        if (allEvents.isNotEmpty()) {
            Logger.log("EventDispatcher", "Flushing ${allEvents.size} events to server")

            // Try to dispatch all events in batches
            val batchSize = PRIMARY_QUEUE_SIZE
            val batches = allEvents.chunked(batchSize)

            for (batch in batches) {
                try {
                    val success = dispatchBatch(batch)
                    if (!success) {
                        Logger.logWarning(
                            "EventDispatcher",
                            "Failed to flush batch of ${batch.size} events"
                        )
                    }
                } catch (e: CancellationException) {
                    // Handle cancellation gracefully during flush
                    Logger.log("EventDispatcher", "Flush cancelled during batch processing")
                    isPendingFlush.set(false)
                    pendingFlushCallback?.invoke()
                    onFlushDone?.invoke()
                    pendingFlushCallback = null
                    return
                } catch (e: Exception) {
                    Logger.logError("EventDispatcher", "Exception during flush", e)
                }
            }
            Logger.log("EventDispatcher", "Flush operation completed")
        } else {
            Logger.log("EventDispatcher", "No events to flush")
        }

        // Reset pending flush state and invoke callbacks
        isPendingFlush.set(false)
        pendingFlushCallback?.invoke()
        onFlushDone?.invoke()
        pendingFlushCallback = null
    }

    fun cleanThroughWorkManager(onCleanUpDone: (() -> Unit)? = null) {
        Logger.log("EventDispatcher", "Saving pending events before cleanup")
        val appContext = context ?: DependencyContainer.getContext()

        // Collect all events from queues before app termination
        val allEvents = mutableListOf<Map<String, String?>>()

        // Add events from primary queue
        while (primaryQueue.isNotEmpty()) {
            primaryQueue.poll()?.let { allEvents.add(it) }
        }

        // Add events from retry queue (highest priority)
        while (retryQueue.isNotEmpty()) {
            retryQueue.poll()?.let { allEvents.add(it) }
        }

        // Add events from overflow queue
        while (overflowQueue.isNotEmpty()) {
            overflowQueue.poll()?.let { allEvents.add(it) }
        }


        Logger.logInfo("EventDispatcher", allEvents.size.toString())

        // Save events to persistent storage synchronously (critical for app termination)
        if (allEvents.isNotEmpty()) {
            try {
                DependencyContainer.getEventPersistenceManager()
                    .savePendingEvents(allEvents, synchronous = true)
                Logger.log(
                    "EventDispatcher",
                    "Saved ${allEvents.size} events to persistent storage"
                )
            } catch (e: Exception) {
                Logger.logError("EventDispatcher", "Failed to save events to persistence", e)
            }
        }

        Logger.log("EventDispatcher", "Scheduling cleanup via WorkManager")

        try {
            val cleanupWorkRequest = OneTimeWorkRequestBuilder<CleanupWorker>()
                .build()

            WorkManager.getInstance(appContext).enqueue(cleanupWorkRequest)
            val workInfo = WorkManager.getInstance(context)
                .getWorkInfoById(cleanupWorkRequest.id)
                .get()  // blocking call
            Logger.log("EventDispatcher", "Cleanup WorkManager request scheduled")
            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                Logger.log("EventDispatcher", "Work Manager Success")
                onCleanUpDone?.invoke()
            }
        } catch (e: Exception) {
            Logger.logError("EventDispatcher", "Failed to schedule cleanup via WorkManager", e)
            onCleanUpDone?.invoke()
        }
    }

    /**
     * Cleanup resources
     * Saves pending events to persistent storage before scheduling WorkManager
     * This ensures events survive process termination
     */
    fun cleanup(onCleanUpDone: (() -> Unit)? = null) {
        Logger.log("EventDispatcher", "Starting cleanup process")

        // Cancel all jobs first
        batchDispatchJob?.cancel()
        pulseJob?.cancel()
        networkMonitorJob?.cancel()

        // Flush all remaining events synchronously
        try {
            dispatcherScope.launch {
                withContext(NonCancellable) {
                    flushAllEvents {
                        onCleanUpDone?.invoke()
                        batchDispatchJob?.cancel()
                        pulseJob?.cancel()
                        networkMonitorJob?.cancel()
                    }
                }
            }
        } catch (e: CancellationException) {
            // Handle cancellation gracefully during cleanup
            Logger.log("EventDispatcher", "Cleanup cancelled - this is expected")
            onCleanUpDone?.invoke()
        } catch (e: Exception) {
            Logger.logError("EventDispatcher", "Error during flush in cleanup", e)
            onCleanUpDone?.invoke()
        }
        Logger.log("EventDispatcher", "Cleanup completed")
    }

    data class QueueStatus(
        val primaryQueueSize: Int,
        val overflowQueueSize: Int,
        val retryQueueSize: Int,
        val isDispatching: Boolean,
        val isNetworkAvailable: Boolean,
        val isPulseScheduled: Boolean,
        val retryAttempts: Int
    )
}
