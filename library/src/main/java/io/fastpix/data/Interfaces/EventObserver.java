package io.fastpix.data.Interfaces;

import org.json.JSONException;

/**
 * Interface representing an event listener.
 * Any class implementing this interface should provide logic for handling events,
 * managing listener IDs, and performing flush operations.
 */
public interface EventObserver {

    /**
     * Sets the unique identifier for this event listener.
     *
     * @param id The ID to be assigned to the listener.
     */
    void setId(int id);

    /**
     * Retrieves the unique identifier of this event listener.
     *
     * @return The ID of the event listener.
     */
    int getId();

    /**
     * Handles the specified event.
     * This method should implement the logic for processing an event when it occurs.
     *
     * @param eventContract The event to be processed.
     * @throws JSONException If an error occurs while handling JSON-based events.
     */
    void handle(EventContract eventContract) throws JSONException;

    /**
     * Flushes any pending event data or state.
     * This method is typically used for cleanup operations or finalizing event data.
     *
     * @throws JSONException If an error occurs during the flush process.
     */
    void flush() throws JSONException;

}
