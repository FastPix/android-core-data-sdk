package io.fastpix.data.monitor;

import org.json.JSONException;

import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.PlayerDataEntity;
import io.fastpix.data.request.CurrentTime;
import io.fastpix.data.Interfaces.TimeMonitor;
import io.fastpix.data.streaming.MediaStreaming;

/**
 * The ExternalHeartbeatTracker class extends the BaseTracker and implements the ITimeTracker interface.
 *
 * This class is responsible for tracking and handling heartbeat events in an external system, such as monitoring
 * the health or activity of an external component or system over time. It inherits basic tracking capabilities from
 * the BaseTracker class and can utilize time-related functionalities from the ITimeTracker interface.
 *
 * As the name suggests, this tracker may be used for sending periodic "heartbeat" signals (or events) to an external
 * service to indicate the system's status or activity. The implementation details would involve periodic time-based events
 * that are tracked and sent to a backend or service for monitoring purposes.
 *
 * The specific behavior of this class depends on the implementation details from BaseTracker and ITimeTracker.
 */
public class RemoteHeartbeatMonitor extends AbstractMonitor implements TimeMonitor {
    private long aLong = 0L;
    private CurrentTime currentTime = new CurrentTime();
    private boolean aBoolean = false;

    /**
     * Constructor for the ExternalHeartbeatTracker class.
     *
     * Initializes the ExternalHeartbeatTracker by calling the constructor of the superclass (BaseTracker) to set up
     * the event dispatcher. The event dispatcher is used for handling events within the tracking system, ensuring that
     * the heartbeat events can be dispatched to the appropriate destination.
     *
     * @param eventEmitter The event dispatcher instance used for dispatching events.
     */
    public RemoteHeartbeatMonitor(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    public void setCurrentTime(CurrentTime currentTime) {
        this.currentTime = currentTime;
    }

    public long now() {
        return this.currentTime.now();
    }

    /**
     * Handles incoming events and processes them accordingly.
     *
     * This method processes events based on their type. Specifically, it tracks the heartbeat events and triggers
     * the dispatch of HeartbeatEvent if certain conditions are met. The method also manages the state of internal
     * heartbeat events and ensures that the heartbeat dispatch happens at the right intervals.
     *
     * @param eventContract The event that is being handled. It can either be trackable or playback-related.
     * @throws JSONException If an error occurs while processing the event data.
     */
    public void handle(EventContract eventContract) throws JSONException {
        if (eventContract.isTrackable()) {
            this.aLong = this.now();
        } else {
            if (eventContract.isPlayback()) {
                if (eventContract.getType().equals("internalHeartbeat")) {
                    if (!this.aBoolean) {
                        this.aLong = 0L;
                    }
                    this.aBoolean = true;
                } else if (eventContract.getType().equals("internalHeartbeatEnd")) {
                    this.aBoolean = false;
                }
                if (this.aBoolean && this.aLong > 0L && this.aLong + 10000L < this.now()) {
                    this.aLong = this.now();
                    StreamingHub streamingHub = (StreamingHub) eventContract;
                    PlayerDataEntity playerData = (streamingHub).getPlayerData();
                    this.dispatch(new MediaStreaming(MediaStreaming.EventType.pulse,playerData));
                }
            }
        }
    }
}
