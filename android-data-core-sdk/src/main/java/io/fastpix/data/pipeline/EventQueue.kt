package io.fastpix.data.pipeline

import io.fastpix.data.domain.model.events.BaseEvent
import io.fastpix.data.utils.Logger
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.atomic.AtomicInteger

/**
 * Bounded in-memory queue for analytics events. Thread-safe.
 * When full, oldest events are dropped and [EVENT_DROPPED] is logged.
 */
class EventQueue(
    private val capacity: Int = DEFAULT_CAPACITY,
    private val onDropped: (() -> Unit)? = null
) {
    private val queue = LinkedBlockingDeque<BaseEvent>(capacity)
    private val droppedCount = AtomicInteger(0)

    @Volatile
    private var isClosed = false

    /**
     * Enqueues an event. If the queue is full, drops the oldest event and adds this one.
     * Does nothing if queue is closed (e.g. during release).
     *
     * @return true if the event was enqueued, false if closed
     */
    fun enqueue(event: BaseEvent): Boolean {
        if (isClosed) return false
        synchronized(queue) {
            if (isClosed) return false
            while (queue.size >= capacity) {
                queue.pollFirst()?.let {
                    droppedCount.incrementAndGet()
                    Logger.logWarning(
                        "EventQueue",
                        "EVENT_DROPPED: queue full (capacity=$capacity), dropped oldest event: ${it.eventName}"
                    )
                    onDropped?.invoke()
                }
            }
            queue.offerLast(event)
        }
        return true
    }

    /**
     * Drains up to [max] events into the given list. Thread-safe.
     */
    fun drainTo(destination: MutableList<BaseEvent>, max: Int): Int {
        if (max <= 0) return 0
        var count = 0
        synchronized(queue) {
            while (count < max) {
                val e = queue.pollFirst() ?: break
                destination.add(e)
                count++
            }
        }
        return count
    }

    /**
     * Drains all events into the given list. Thread-safe.
     */
    fun drainAllTo(destination: MutableList<BaseEvent>): Int {
        return drainTo(destination, Int.MAX_VALUE)
    }

    val size: Int
        get() = queue.size

    val isEmpty: Boolean
        get() = queue.isEmpty()

    fun close() {
        isClosed = true
    }

    fun getDroppedCount(): Int = droppedCount.get()

    companion object {
        const val DEFAULT_CAPACITY = 500
    }
}
