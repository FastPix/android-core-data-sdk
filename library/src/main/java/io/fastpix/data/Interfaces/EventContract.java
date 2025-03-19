package io.fastpix.data.Interfaces;

/**
 * Interface representing an event in the system.
 * Any event class implementing this interface should provide the behavior
 * for determining the type of event, whether it has specific properties like session data,
 * trackability, error status, and more.
 */
public interface EventContract {

    /**
     * Returns the type of the event.
     *
     * @return A string representing the event type.
     */
    String getType();

    /**
     * Indicates whether the event contains session-related data.
     *
     * @return {@code true} if the event contains session data, {@code false} otherwise.
     */
    boolean isSessionData();

    /**
     * Indicates whether the event is trackable.
     *
     * @return {@code true} if the event is trackable, {@code false} otherwise.
     */
    boolean isTrackable();

    /**
     * Indicates whether the event is related to playback.
     *
     * @return {@code true} if the event is related to playback, {@code false} otherwise.
     */
    boolean isPlayback();

    /**
     * Indicates whether the event contains data-related information.
     *
     * @return {@code true} if the event contains data, {@code false} otherwise.
     */
    boolean isData();

    /**
     * Indicates whether the event represents an error.
     *
     * @return {@code true} if the event is an error, {@code false} otherwise.
     */
    boolean isError();

    /**
     * Indicates whether the event is related to view metrics.
     *
     * @return {@code true} if the event is a view metric, {@code false} otherwise.
     */
    boolean isViewMetric();

    /**
     * Indicates whether the event is used for debugging purposes.
     *
     * @return {@code true} if the event is a debug event, {@code false} otherwise.
     */
    boolean isDebug();

}
