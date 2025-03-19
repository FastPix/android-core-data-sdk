package io.fastpix.data.streaming;

import org.json.JSONException;

/**
 * Represents an event triggered when the event rate exceeds a predefined threshold.
 * This class extends {@link MonitoredEventContract} and modifies its query data.
 */
public class OveruseDetectedEventContract extends MonitoredEventContract {
    public static final String TYPE = "eventRateExceeded"; // Constant event type

    /**
     * Constructs an {@code EventRateExceedTrackableEvent} using an existing {@link MonitoredEventContract}.
     * Copies and updates the query data from the provided trackable event.
     * @param monitoredEvent The original trackable event whose query data is updated.
     * @throws JSONException If an error occurs while updating the query data.
     */
    public OveruseDetectedEventContract(MonitoredEventContract monitoredEvent) throws JSONException {
        super(TYPE);
        monitoredEvent.getQuery().update(this.getQuery()); // Copy query data from trackableEvent
    }
}
