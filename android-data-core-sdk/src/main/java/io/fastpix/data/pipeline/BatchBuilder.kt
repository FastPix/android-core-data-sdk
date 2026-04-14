package io.fastpix.data.pipeline

import io.fastpix.data.domain.model.events.BaseEvent

/**
 * Thread-safe batch builder. Groups events into batches. Caller should send when [shouldFlush] is true
 * (batch size >= target or explicit flush).
 */
class BatchBuilder(
    private val batchSizeThreshold: Int = DEFAULT_BATCH_SIZE
) {
    private val buffer = mutableListOf<BaseEvent>()
    private val lock = Any()

    /**
     * Adds an event to the current batch.
     */
    fun add(event: BaseEvent) {
        synchronized(lock) {
            buffer.add(event)
        }
    }

    /**
     * Adds multiple events.
     */
    fun addAll(events: List<BaseEvent>) {
        if (events.isEmpty()) return
        synchronized(lock) {
            buffer.addAll(events)
        }
    }

    /**
     * True when the current batch has reached the size threshold.
     */
    fun shouldFlush(): Boolean = synchronized(lock) { buffer.size >= batchSizeThreshold }

    /**
     * Takes the current batch and clears the buffer. Returns an empty list if nothing to send.
     */
    fun takeBatch(): List<BaseEvent> {
        return synchronized(lock) {
            if (buffer.isEmpty()) emptyList()
            else buffer.toList().also { buffer.clear() }
        }
    }

    /**
     * Takes all remaining events (for flush on release). Clears the buffer.
     */
    fun takeAll(): List<BaseEvent> {
        return synchronized(lock) {
            buffer.toList().also { buffer.clear() }
        }
    }

    val size: Int get() = synchronized(lock) { buffer.size }
    val isEmpty: Boolean get() = synchronized(lock) { buffer.isEmpty() }

    companion object {
        const val DEFAULT_BATCH_SIZE = 20
    }
}
