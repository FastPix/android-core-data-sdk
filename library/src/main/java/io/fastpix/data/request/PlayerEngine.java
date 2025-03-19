package io.fastpix.data.request;

import org.json.JSONException;
import java.util.Objects;
import io.fastpix.data.streaming.EventHandler;
import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.streaming.ChainedEventObserver;
import io.fastpix.data.streaming.UserSessionEventContract;
import io.fastpix.data.streaming.MonitoredEventContract;
import io.fastpix.data.streaming.StreamingData;
import io.fastpix.data.Interfaces.PlaybackEventContract;
import io.fastpix.data.entity.CustomerPlayerDataEntity;
import io.fastpix.data.entity.PlayerDataEntity;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.entity.ViewDeviceOrientationDataEntity;
import io.fastpix.data.monitor.SignalBatchMonitor;
import io.fastpix.data.streaming.MediaStreaming;


/**
 * CorePlayer is an extension of EventBus, responsible for handling
 * playback events, managing event listeners, and dispatching updates.
 * It acts as the core component that interacts with various event types
 * within the media playback framework.
 */
public class PlayerEngine extends EventHandler {
    private final PlayerDataEntity playerData = new PlayerDataEntity();
    private final CustomerPlayerDataEntity customerPlayerData = new CustomerPlayerDataEntity();
    private ViewEngine viewEngine;
    private int playerSequenceNumberInt = 0;
    private final SignalBatchMonitor signalBatchMonitor;

    /**
     * Constructor for CorePlayer.
     * Initializes tracking components and assigns a unique player instance ID.
     *
     * @param customOptions Configuration options for customizing player behavior.
     * @throws JSONException If an error occurs while handling JSON-related operations.
     */
    public PlayerEngine(CustomOptions customOptions) throws JSONException {
        this.signalBatchMonitor = new SignalBatchMonitor(customOptions);
        this.playerData.setPlayerInstanceId(String.valueOf(java.util.UUID.randomUUID()));
    }

    /**
     * Dispatches an event to the appropriate handler based on its type.
     * This method updates player data, handles playback events, session data,
     * and other events, ensuring they are correctly routed and processed.
     *
     * @param eventContract The event to be dispatched.
     * @throws JSONException If there is an error during JSON processing.
     */
    public synchronized void dispatch(EventContract eventContract) throws JSONException {
        if (eventContract.isTrackable()) {
            MonitoredEventContract monitoredEvent = (MonitoredEventContract) eventContract;
            this.playerData.update(monitoredEvent.getPlayerData());
            this.playerData.setPlayerSequenceNumber(++this.playerSequenceNumberInt);
            monitoredEvent.setPlayerData(this.playerData);
            monitoredEvent.setCustomerPlayerData(this.customerPlayerData);
            super.dispatch(eventContract);
        } else if (eventContract.isError()) {
            super.dispatch(eventContract);
        } else {
            if (eventContract.isPlayback() || eventContract.isData()) {
                if (eventContract.isSessionData()) {
                    UserSessionEventContract userSessionEvent = (UserSessionEventContract) eventContract;
                    CustomerPlayerDataEntity customerPlayerData1 = userSessionEvent.getCustomerPlayerData();
                    CustomerPlayerDataEntity customerPlayerData2 = this.customerPlayerData;
                    Objects.requireNonNull(customerPlayerData2);
                    Helper.let(customerPlayerData1, customerPlayerData2::update);
                } else if (eventContract.isPlayback()) {
                    PlaybackEventContract iPlaybackEvent = (PlaybackEventContract) eventContract;
                    this.playerData.update(iPlaybackEvent.getPlayerData());
                    if (Objects.equals(iPlaybackEvent.getType(), "viewInit")) {
                        if (this.viewEngine != null) {
                            this.viewEngine.removeAllListeners();
                        }
                        this.viewEngine = new ViewEngine();
                        this.viewEngine.addListener(new ChainedEventObserver(this));
                        this.playerData.setPlayerErrorCode((String)null);
                        this.playerData.setPlayerErrorMessage((String)null);
                    }
                } else {
                    StreamingData streamingData = (StreamingData) eventContract;
                    this.customerPlayerData.update(streamingData.getCustomerPlayerData());
                }
                if (this.viewEngine != null) {
                    this.viewEngine.dispatch(eventContract);
                }
            }
        }
    }

    /**
     * Handles the event by passing it to the beacon batch tracker.
     * This method allows the event to be processed or tracked by the BeaconBatchTracker.
     *
     * @param eventContract The event to be handled.
     * @throws JSONException If there is an error processing the event in JSON format.
     */
    public void handle(EventContract eventContract) throws JSONException {
        this.signalBatchMonitor.handle(eventContract);
    }

    /**
     * Configures the logging and verbose output behavior for the player.
     * This method sets whether logcat output is allowed and adjusts the verbose mode
     * of the beacon batch tracker.
     *
     * @param logcat If true, allows logcat output; otherwise, disables it.
     * @param isTrue If true, enables verbose mode in the beacon batch tracker.
     */
    public void allowLogcatOutput(boolean logcat, boolean isTrue) {
        AnalyticsEventLogger.setAllowLogcat(logcat);
        this.signalBatchMonitor.setVerboseMode(isTrue);
    }

    /**
     * Dispatches an orientation change event based on the provided FPSDK view orientation.
     * This method creates an event that reflects the new orientation (portrait or landscape)
     * and dispatches it to notify listeners or components that need to react to the change.
     *
     * @param playerViewOrientation The new orientation of the player view (portrait or landscape).
     * @throws JSONException If there is an error processing the event data.
     */
    public void dispatchOrientationChange(PlayerViewOrientation playerViewOrientation) throws JSONException {
        ViewDeviceOrientationDataEntity viewDeviceOrientationData= new ViewDeviceOrientationDataEntity();
        viewDeviceOrientationData.setOrientationX(0);
        viewDeviceOrientationData.setOrientationY(0);
        switch (playerViewOrientation) {
            case PORTRAIT:
                viewDeviceOrientationData.setOrientationZ(90);
                break;
            case LANDSCAPE:
                viewDeviceOrientationData.setOrientationZ(0);
                break;
            default:
                return;
        }

        ViewDataEntity viewData= new ViewDataEntity();
        viewData.setViewOrientationData(viewDeviceOrientationData);
        MediaStreaming orientationChangeEvent = new MediaStreaming(MediaStreaming.EventType.orientationChange,this.playerData);
        orientationChangeEvent.setViewData(viewData);
        this.dispatch(orientationChangeEvent);
    }

    /**
     * Flushes the beacon batch tracker to ensure all the tracked events are processed and sent.
     * This method also releases any resources held by the beacon batch tracker.
     *
     * @throws JSONException If there is an error during the flushing process.
     */
    public void flush() throws JSONException {
        this.signalBatchMonitor.flush();
        this.signalBatchMonitor.release();
    }
}
