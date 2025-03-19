package io.fastpix.data.streaming;


import io.fastpix.data.Interfaces.EventContract;

/**
 * A base implementation of the {@link EventContract} interface.
 * This class represents a generic event with various properties
 * that can be overridden in subclasses to define specific event behaviors.
 */
public class AbstractEventContract implements EventContract {
    public static final String TYPE = "baseEvent"; // Constant event type

    /**
     * Default constructor for BaseEvent.
     */
    public AbstractEventContract() {
    }

    /**
     * Returns the type of the event.
     * @return The event type as a string.
     */
    public String getType() {
        return TYPE;
    }

    /**
     * Indicates whether this event contains session-related data.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isSessionData() {
        return false;
    }

    /**
     * Indicates whether this event is trackable.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isTrackable() {
        return false;
    }

    /**
     * Indicates whether this event is related to media playback.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isPlayback() {
        return false;
    }

    /**
     * Indicates whether this event contains data-related information.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isData() {
        return false;
    }

    /**
     * Indicates whether this event represents an error.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isError() {
        return false;
    }

    /**
     * Indicates whether this event is related to view metrics.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isViewMetric() {
        return false;
    }

    /**
     * Indicates whether this event is used for debugging purposes.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isDebug() {
        return false;
    }
}
