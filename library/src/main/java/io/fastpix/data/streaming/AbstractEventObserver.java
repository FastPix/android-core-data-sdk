package io.fastpix.data.streaming;

import android.util.Log;
import org.json.JSONException;

import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.Interfaces.EventObserver;

/**
 * A base implementation of the {@link EventObserver} interface.
 * This class provides default behavior for handling events,
 * setting and retrieving an ID, and flushing event data.
 */
public class AbstractEventObserver implements EventObserver {
    private int idValue; // Unique identifier for the event listener

    /**
     * Default constructor.
     * Currently, it only logs an empty message (likely for debugging purposes).
     */
    public AbstractEventObserver() {
    }

    /**
     * Sets the unique identifier for this event listener.
     * @param id The ID to be assigned.
     */
    public void setId(int id) {
        this.idValue = id;
    }

    /**
     * Retrieves the unique identifier of this event listener.
     * @return The ID of this event listener.
     */
    public int getId() {
        return this.idValue;
    }

    /**
     * Handles the received event. This method should be overridden in subclasses
     * to provide specific event-handling logic.
     * @param eventContract The event to be processed.
     * @throws JSONException If an error occurs while handling JSON-based events.
     */
    public void handle(EventContract eventContract) throws JSONException {
        Log.e("BaseEventListener", "handle method called");
    }

    /**
     * Flushes any pending event data or state.
     * Should be overridden if necessary to perform cleanup operations.
     * @throws JSONException If an error occurs while flushing event data.
     */
    public void flush() throws JSONException {
        Log.e("BaseEventListener", "flush method called");
    }
}
