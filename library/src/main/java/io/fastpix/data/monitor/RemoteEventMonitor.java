package io.fastpix.data.monitor;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.MonitoredEventContract;
import io.fastpix.data.streaming.StreamingHub;
import org.json.JSONException;
import java.util.HashSet;

/**
 * The ExternalEventTracker class extends the BaseTracker class and is responsible for handling
 * and tracking external events that occur in the application. It provides functionality for
 * processing events that are external to the core application flow, such as user interactions
 * or system-triggered events.
 *
 * This class can be used to monitor and log external events for analytics, tracking user
 * behavior, or integrating with other systems. It extends the base tracker functionality
 * to cater to external events specifically.
 *
 * It utilizes the methods and properties of the BaseTracker class to manage event dispatching,
 * timing, and data collection, while adding its own specialized handling for external events.
 */
public class RemoteEventMonitor extends AbstractMonitor {
    private final HashSet<String> hashSet = new HashSet();

    /**
     * Constructor for the ExternalEventTracker class.
     *
     * This constructor initializes the ExternalEventTracker with the provided event dispatcher.
     * The event dispatcher is responsible for handling the communication and dispatching of events.
     * By passing in an IEventDispatcher implementation, this tracker can interact with the event
     * system and perform its event tracking duties.
     *
     * @param eventEmitter The event dispatcher that will be used to handle event dispatching.
     */
    public RemoteEventMonitor(EventEmitter eventEmitter) {
        super(eventEmitter);
        this.hashSet.add("ended");
        this.hashSet.add("error");
        this.hashSet.add("pulse");
        this.hashSet.add("pageLoadStart");
        this.hashSet.add("pause");
        this.hashSet.add("play");
        this.hashSet.add("playerReady");
        this.hashSet.add("playing");
        this.hashSet.add("buffered");
        this.hashSet.add("buffering");
        this.hashSet.add("sampling");
        this.hashSet.add("seeked");
        this.hashSet.add("seeking");
        this.hashSet.add("stalled");
        this.hashSet.add("videoChange");
        this.hashSet.add("viewCompleted");
        this.hashSet.add("viewBegin");
        this.hashSet.add("waiting");
        this.hashSet.add("variantChanged");
        this.hashSet.add("orientationChange");
        this.hashSet.add("requestCompleted");
        this.hashSet.add("requestCanceled");
        this.hashSet.add("requestFailed");
    }

    /**
     * Handles playback events and dispatches trackable events based on the type of playback event.
     *
     * This method listens for specific playback events, checks if they should be processed (i.e., if they
     * belong to a predefined set of event types and are not suppressed), and creates a TrackableEvent accordingly.
     * If the playback event contains bandwidth metric data or ad data, this data is added to the TrackableEvent.
     * The resulting TrackableEvent is then dispatched for further processing.
     *
     * @param streamingHub The playback event to be processed, containing various data related to the playback.
     * @throws JSONException If an error occurs while processing the event data (e.g., while parsing JSON data).
     */
    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        if (this.hashSet.contains(streamingHub.getType()) && !streamingHub.isSuppressed()) {
            MonitoredEventContract monitoredEvent = new MonitoredEventContract(streamingHub.getType());
            if (streamingHub.getBandwidthMetricData() != null) {
                monitoredEvent.setBandwidthMetricData(streamingHub.getBandwidthMetricData());
            }
            this.dispatch(monitoredEvent);
        }
    }
}
