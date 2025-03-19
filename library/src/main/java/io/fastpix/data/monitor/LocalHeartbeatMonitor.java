package io.fastpix.data.monitor;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.PlayerDataEntity;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.streaming.MediaStreaming;

import org.json.JSONException;

/**
 * InternalHeartbeatTracker is responsible for tracking internal heartbeat events
 * within the playback environment. It extends the BaseTracker class to inherit
 * its basic tracking functionalities and manages specific heartbeat events.
 * This class handles heartbeat event lifecycle and dispatches necessary heartbeat
 * events based on playback progress.
 */
public class LocalHeartbeatMonitor extends AbstractMonitor {
    private boolean aBoolean = false;

    /**
     * Constructor for InternalHeartbeatTracker.
     *
     * Initializes the InternalHeartbeatTracker with the provided event dispatcher.
     * This constructor invokes the parent class (BaseTracker) constructor to
     * initialize the dispatcher, which is responsible for dispatching events.
     *
     * @param eventEmitter The event dispatcher used to handle event dispatching.
     */
    public LocalHeartbeatMonitor(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    /**
     * Handles playback-related events and processes them according to the event type.
     *
     * This method checks the type of the playback event (e.g., "timeUpdate", "buffering",
     * "playing", "pause") and performs different actions based on the event type. For each
     * event type, it either processes player data or view data, or updates internal flags
     * like `aBoolean` to track the state of playback. This enables the tracker to respond
     * to playback changes such as play, pause, seeking, and error states.
     * The method ensures that appropriate actions are taken depending on whether the
     * playback is ongoing, paused, or completed, and may trigger data updates or event
     * dispatches as necessary.
     *
     * @param streamingHub The event that is being processed, containing player and view data.
     * @throws JSONException If an error occurs while processing the playback event data.
     */
    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        switch (streamingHub.getType()) {
            case "timeUpdate":
            case "buffering":
            case "buffered":
                if (this.aBoolean) {
                    this.bData(streamingHub.getPlayerData(), streamingHub.getViewData());
                }
                return;
            case "playing":
                this.aBoolean = true;
                this.bData(streamingHub.getPlayerData(), streamingHub.getViewData());
                return;
            case "play":
            case "seeking":
                PlayerDataEntity playerData = streamingHub.getPlayerData();
                ViewDataEntity viewData = streamingHub.getViewData();
                PlayerDataEntity playerData1 = playerData;
                if (!this.aBoolean) {
                    this.aBoolean = true;
                    this.bData(playerData1, viewData);
                }
                return;
            case "pause":
            case "ended":
            case "viewCompleted":
            case "error":
                this.aData(streamingHub.getPlayerData(), streamingHub.getViewData());
                return;
            case "seeked":
                if (Boolean.TRUE.equals(streamingHub.getPlayerData().getPlayerIsPaused())) {
                    this.aData(streamingHub.getPlayerData(), streamingHub.getViewData());
                }
                return;
            default:
                break;
        }
    }

    /**
     * Handles the end of the internal heartbeat event and dispatches the corresponding event.
     *
     * This method processes the end of an internal heartbeat event by checking the state of
     * the `aBoolean` flag. If the flag is set to true, it creates an `InternalHeartbeatEnd`
     * using the provided `PlayerData`, marks the heartbeat as ended by setting `aBoolean` to false,
     * and then dispatches the `InternalHeartbeatEnd`. The `ViewData` is also associated with
     * the event to provide complete context.
     *
     * @param playerData The player data associated with the event.
     * @param viewData The view data associated with the event.
     * @throws JSONException If an error occurs while processing the data or event.
     */
    private void aData(PlayerDataEntity playerData, ViewDataEntity viewData) throws JSONException {
        if (this.aBoolean) {
            this.aBoolean = false;
            MediaStreaming internalHeartbeatEndEvent= new MediaStreaming(MediaStreaming.EventType.internalHeartbeatEnd,playerData);
            internalHeartbeatEndEvent.setViewData(viewData);
            this.dispatch(internalHeartbeatEndEvent);
        }
    }

    /**
     * Handles the internal heartbeat event by dispatching the corresponding event.
     *
     * This method processes the ongoing internal heartbeat event. It creates an `InternalHeartbeat`
     * using the provided `PlayerData`, and associates the `ViewData` with the event. After setting up the event,
     * it dispatches the `InternalHeartbeat` to notify the system about the current heartbeat status.
     *
     * @param playerData The player data associated with the event.
     * @param viewData The view data associated with the event.
     * @throws JSONException If an error occurs while processing the data or event.
     */
    private void bData(PlayerDataEntity playerData, ViewDataEntity viewData) throws JSONException {
        MediaStreaming internalHeartbeatEvent= new MediaStreaming(MediaStreaming.EventType.internalHeartbeat,playerData);
        (internalHeartbeatEvent ).setViewData(viewData);
        this.dispatch(internalHeartbeatEvent);
    }
}
