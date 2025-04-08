package io.fastpix.data.request;

import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.fastpix.data.streaming.AbstractEventObserver;
import io.fastpix.data.Interfaces.DeviceContract;
import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.streaming.InternalErrorEvent;
import io.fastpix.data.streaming.UserSessionEventContract;
import io.fastpix.data.streaming.StreamingData;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.CustomerDataEntity;
import io.fastpix.data.entity.CustomerPlayerDataEntity;
import io.fastpix.data.entity.CustomerVideoDataEntity;
import io.fastpix.data.entity.CustomerViewDataEntity;
import io.fastpix.data.entity.WorkSpaceEntity;
import io.fastpix.data.entity.PlayerDataEntity;
import io.fastpix.data.entity.UserSessionTag;
import io.fastpix.data.entity.VideoDataEntity;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.entity.ViewerDataEntity;
import io.fastpix.data.Interfaces.RequestHandler;
import io.fastpix.data.Interfaces.PlayerObserver;
import io.fastpix.data.streaming.MediaStreaming;
import io.fastpix.data.streaming.SupportStreaming;

/**
 * FPStats is a class that extends BaseEventListener and is responsible for handling
 * and processing events related to video player statistics and performance metrics.
 * This class listens for specific events such as playback, buffering, and data metrics
 * and tracks related statistics such as view time, rebuffer duration, and throughput.
 * It dispatches updated data and metrics to the appropriate listeners or storage systems.
 */
public class FastPixMetrics extends AbstractEventObserver {
    protected static final String FP_STATS = "FPStats";
    private Timer timer;
    private final String stringCore;
    private CustomerDataEntity customerData;
    private Integer dPlayerViewWidth;
    private Integer ePlayerViewHeight;
    private Integer fPlayerHeight;
    private Integer gPlayerWidth;
    private VideoDataEntity videoData;
    private long iDroppedCount;
    private String jMessage;
    private int kExceptionCode;
    private String lContext;
    private boolean isPlayerSizeChange;
    private MediaPresentation mediaPresentation;
    private Boolean isPreLoaded = false;
    private String oFPViewerId;
    private String pViewerApplicationName;
    private String qAppVersion;
    private boolean isCallFPStats;
    private final CustomOptions customOptions;
    private PlayerEngine playerEngine;
    private PlayerObserver playerObserver;
    private static DeviceContract deviceContract = null;
    private static RequestHandler requestHandler = null;
    private CurrentTime currentTime;

    /**
     * Constructs an FPStats object with the provided listener, string, customer data,
     * and default custom options.
     *
     * @param playerObserver The listener for player events.
     * @param string A string parameter (could be used for player ID or other identifiers).
     * @param customerData The customer data associated with the player.
     * @throws JSONException If there is an error in parsing or handling JSON data.
     */
    public FastPixMetrics(PlayerObserver playerObserver, String string, CustomerDataEntity customerData) throws JSONException {
        this(playerObserver, string, customerData, new CustomOptions());
    }

    /**
     * Constructs an FPStats object with the provided listener, string, customer data,
     * and custom options.
     *
     * @param playerObserver The listener for player events, which handles player-specific actions.
     * @param string A string parameter (likely representing an identifier such as a player ID or other key).
     * @param customerData The customer data associated with the player, containing specific information for tracking.
     * @param customOptions Custom configuration options to modify the behavior of the FPStats instance.
     * @throws JSONException If there is an error in parsing or handling JSON data.
     */
    public FastPixMetrics(PlayerObserver playerObserver, String string, CustomerDataEntity customerData, CustomOptions customOptions) throws JSONException {
        this.videoData = new VideoDataEntity();
        this.customerData = customerData;
        this.stringCore = string;
        this.isCallFPStats = true;
        this.isPlayerSizeChange = false;
        this.customOptions = customOptions;
        if (this.customOptions == null) {
            throw new IllegalArgumentException("customOptions cannot be null");
        } else if (this.stringCore == null) {
            throw new IllegalArgumentException("playerName cannot be null");
        } else if (this.customerData != null && this.customerData.getCustomerPlayerData() != null) {
            this.playerEngine = Hub.createPlayer(this.stringCore, this.customOptions);
            this.iDroppedCount = 0L;
            this.playerObserver = playerObserver;
            this.aLocal();
            PlayerDataEntity playerData = this.getPlayerData();
            this.aEventParam(new MediaStreaming(MediaStreaming.EventType.viewInit, playerData));
            this.timer = new Timer();
            this.timer.scheduleAtFixedRate(new ParamsWithFPStatsA(this, this.timer), 0L, 100L);
            this.videoData = new VideoDataEntity();
            StreamingData streamingData = new StreamingData();
            if (this.customerData != null && this.customerData.getCustomerPlayerData() != null) {
                streamingData.setCustomerPlayerData(this.customerData.getCustomerPlayerData());
            }
            if (this.customerData != null && this.customerData.getCustomerVideoData() != null) {
                streamingData.setCustomerVideoData(this.customerData.getCustomerVideoData());
            }
            if (this.customerData != null && this.customerData.getCustomerViewData() != null) {
                streamingData.setCustomerViewData(this.customerData.getCustomerViewData());
            }
            if (this.customerData != null && this.customerData.getCustomData() != null) {
                streamingData.setCustomData(this.customerData.getCustomData());
            }
            if (this.customerData != null && this.customerData.getCustomerViewerData() != null) {
                streamingData.setCustomerViewerData(this.customerData.getCustomerViewerData());
            }
            if (this.customerData != null && (this.customerData.getCustomerPlayerData() != null || this.customerData.getCustomerVideoData() != null || this.customerData.getCustomerViewData() != null || this.customerData.getCustomData() != null || this.customerData.getCustomerViewerData() != null)) {
                this.aEventParam(streamingData);
            }
            this.aEventParam(new MediaStreaming(MediaStreaming.EventType.playerReady, playerData));
        }
    }

    public String getBeaconDomain() {
        return this.customOptions.getBeaconDomain();
    }

    public CustomerDataEntity getCustomerData() {
        return this.customerData;
    }

    public void setCurrentTime(CurrentTime currentTime) {
        this.currentTime = currentTime;
    }

    public long now() {
        return this.currentTime.now();
    }

    public void setCustomerData(CustomerDataEntity customerData) throws JSONException {
        if (customerData != null && customerData.getCustomerPlayerData() != null) {
            StreamingData streamingData = new StreamingData();
            if (customerData.getCustomerViewData() != null) {
                streamingData.setCustomerViewData(customerData.getCustomerViewData());
            }
            if (customerData.getCustomerPlayerData() != null) {
                streamingData.setCustomerPlayerData(customerData.getCustomerPlayerData());
            }
            if (customerData.getCustomerVideoData() != null) {
                streamingData.setCustomerVideoData(customerData.getCustomerVideoData());
            }
            if (customerData.getCustomData() != null) {
                streamingData.setCustomData(customerData.getCustomData());
            }
            if (customerData.getCustomerViewerData() != null) {
                streamingData.setCustomerViewerData(customerData.getCustomerViewerData());
            }
            this.customerData = customerData;
            this.aEventParam(streamingData);
        } else {
            throw new IllegalArgumentException("customerPlayerData cannot be null");
        }
    }

    public CustomerVideoDataEntity getCustomerVideoData() {
        return this.customerData != null ? this.customerData.getCustomerVideoData() : null;
    }

    public CustomerViewDataEntity getCustomerViewData() {
        return this.customerData != null ? this.customerData.getCustomerViewData() : null;
    }

    public CustomerPlayerDataEntity getCustomerPlayerData() {
        return this.customerData != null ? this.customerData.getCustomerPlayerData() : null;
    }

    /**
     * Updates the customer-related data and dispatches a DataEvent.
     * This method ensures that the provided customer data (video, player, view)
     * is updated and associated with the current customer data.
     *
     * @param customerPlayerData The player-specific customer data to be updated.
     * @param customerVideoData The video-specific customer data to be updated.
     * @throws JSONException If there is an error processing the JSON data.
     */
    public void updateCustomerData(CustomerPlayerDataEntity customerPlayerData, CustomerVideoDataEntity customerVideoData) throws JSONException {
        this.updateCustomerData(customerPlayerData, customerVideoData, null);
    }

    /**
     * Updates the customer-related data and dispatches a DataEvent.
     * This method ensures that the provided customer data (video, player, view)
     * is updated and associated with the current customer data.
     *
     * @param customerPlayerData The player-specific customer data to be updated.
     * @param customerVideoData The video-specific customer data to be updated.
     * @param customerViewData The view-specific customer data to be updated.
     * @throws JSONException If there is an error processing the JSON data.
     */
    public void updateCustomerData(CustomerPlayerDataEntity customerPlayerData, CustomerVideoDataEntity customerVideoData, CustomerViewDataEntity customerViewData) throws JSONException {
        if (customerVideoData != null || customerPlayerData != null || customerViewData != null) {
            StreamingData streamingData = new StreamingData();
            if (customerVideoData != null) {
                customerVideoData.setVideoDuration(playerObserver.getSourceDuration());
                this.customerData.setCustomerVideoData(customerVideoData);
                streamingData.setCustomerVideoData(customerVideoData);
            }
            if (customerPlayerData != null) {
                this.customerData.setCustomerPlayerData(customerPlayerData);
                streamingData.setCustomerPlayerData(customerPlayerData);
            }
            if (customerViewData != null) {
                this.customerData.setCustomerViewData(customerViewData);
                streamingData.setCustomerViewData(customerViewData);
            }
            this.aEventParam(streamingData);
        }
    }

    public static void setHostDevice(DeviceContract deviceContract) {
        FastPixMetrics.deviceContract = deviceContract;
    }

    public static DeviceContract getHostDevice() {
        return deviceContract;
    }

    public static void setHostNetworkApi(RequestHandler requestHandler) {
        FastPixMetrics.requestHandler = requestHandler;
    }

    /**
     * Retrieves the instance of the network request API for host communication.
     * This method provides access to the network request API to perform operations
     * such as making API calls, handling requests, etc.
     *
     * @return The instance of the INetworkRequest interface used to interact with the host API.
     */
    public static RequestHandler getHostNetworkApi() {
        return requestHandler;
    }

    public void allowLogcatOutput(boolean logcat, boolean isTrue) {
        Hub.allowLogcatOutputForPlayer(this.stringCore, logcat, isTrue);
    }

    public void orientationChange(PlayerViewOrientation playerViewOrientation) throws JSONException {
        Hub.orientationChangeForPlayer(this.stringCore, playerViewOrientation);
    }

    public void presentationChange(@javax.annotation.Nullable MediaPresentation mediaPresentation) {
        this.mediaPresentation = mediaPresentation;
    }

    public void setPreLoaded(Boolean isTrue) {
        this.isPreLoaded = isTrue;
    }

    public void setSessionData(@Nullable List<UserSessionTag> userSessionTags) throws Exception {
        Hub.dispatchEventForPlayer(this.stringCore, new UserSessionEventContract(userSessionTags));
    }

    public void videoChange(CustomerVideoDataEntity customerVideoData) throws JSONException {
        this.customerData.setCustomerVideoData(customerVideoData);
        this.videoChange(this.customerData);
        this.iDroppedCount = 0L;
    }

    /**
     * Handles a change in the video, triggering necessary events and updating data.
     * This method is called when there is a change in the video being played.
     * It initializes the necessary events (view end and view start), updates the
     * customer data, and sets up the video data for the new video.
     *
     * @param customerData The customer data to be updated with new video information.
     * @throws JSONException If there is an error processing the event data.
     */
    public void videoChange(CustomerDataEntity customerData) throws JSONException {
        this.aEventParam(new MediaStreaming(MediaStreaming.EventType.viewCompleted, this.getPlayerData()));
        this.aEventParam(new MediaStreaming(MediaStreaming.EventType.viewInit, this.getPlayerData()));
        this.customerData = customerData;
        StreamingData streamingData = new StreamingData();
        if (this.customerData.getCustomerVideoData() != null) {
            streamingData.setCustomerVideoData(this.customerData.getCustomerVideoData());
        }
        if (this.customerData.getCustomData() != null) {
            streamingData.setCustomData(this.customerData.getCustomData());
        }
        if (this.customerData.getCustomerViewData() != null) {
            streamingData.setCustomerViewData(this.customerData.getCustomerViewData());
        }
        this.videoData = new VideoDataEntity();
        streamingData.setVideoData(this.videoData);
        this.aEventParam(streamingData);
        this.jMessage = null;
        this.kExceptionCode = 0;
        this.lContext = null;
    }

    /**
     * Sets the `ViewProgramChanged` flag on the provided PlaybackEvent and ensures that
     * the event has valid ViewData.
     * If the PlaybackEvent does not already have ViewData, a new instance is created
     * and the `viewProgramChanged` flag is set to `true`. If ViewData exists, the flag
     * is set on the existing data.
     *
     * @param streamingHub The PlaybackEvent to be updated.
     * @return The updated PlaybackEvent with the `viewProgramChanged` flag set.
     * @throws JSONException If there is an error setting the event data.
     */
    private static StreamingHub aParamWithPlayBackEvent(StreamingHub streamingHub) throws JSONException {
        if (streamingHub.getViewData() == null) {
            ViewDataEntity viewData = new ViewDataEntity();
            (viewData).setViewProgramChanged(Boolean.TRUE);
            streamingHub.setViewData(viewData);
        } else {
            streamingHub.getViewData().setViewProgramChanged(Boolean.TRUE);
        }
        return streamingHub;
    }

    /**
     * Handles a program change by triggering necessary events and updating data.
     * It performs the following:
     * 1. Calls `videoChange()` to update the video-related data.
     * 2. Creates a new `Play` and a `Playing` with the current player data,
     *    setting the `viewProgramChanged` flag via `aParamWithPlayBackEvent()`.
     * 3. Logs the event with a tag "playevent" for debugging purposes.
     *
     * @param customerVideoData The new video data associated with the program change.
     * @throws JSONException If there is an error while handling the event or updating the data.
     */
    public void programChange(CustomerVideoDataEntity customerVideoData) throws JSONException {
        this.videoChange(customerVideoData);
        this.aEventParam(aParamWithPlayBackEvent(new MediaStreaming(MediaStreaming.EventType.play, this.getPlayerData())));
        Log.e("playevent", "programChange");
        this.aEventParam(aParamWithPlayBackEvent(new MediaStreaming(MediaStreaming.EventType.playing, this.getPlayerData())));
    }

    public void setPlayerSize(int height, int width) {
        height = aParamsWithInt(height, 0, 1048576);
        width = aParamsWithInt(width, 0, 1048576);
        this.isPlayerSizeChange = true;
        this.dPlayerViewWidth = height;
        this.ePlayerViewHeight = width;
    }

    public void setScreenSize(int height, int width) {
        this.fPlayerHeight = height;
        this.gPlayerWidth = width;
    }

    public void error(RequestFailureException requestFailureException) throws JSONException {
        this.jMessage = requestFailureException.getMessage();
        this.kExceptionCode = requestFailureException.getCode();
        this.lContext = requestFailureException.getContext();
        AnalyticsEventLogger.d(FP_STATS, "external error (" + this.kExceptionCode + "): " + this.jMessage);
        this.updateEventsFromLocal();
        this.aEventParam(new MediaStreaming(MediaStreaming.EventType.error, this.getPlayerData()));
    }

    public void setAutomaticErrorTracking(boolean automaticErrorTracking) {
        this.isCallFPStats = automaticErrorTracking;
    }

    public void release() throws JSONException {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer.purge();
            this.timer = null;
        }
        if (this.stringCore != null) {
            this.aEventParam(new MediaStreaming(MediaStreaming.EventType.viewCompleted, this.getPlayerData()));
            Hub.destroyPlayer(this.stringCore);
        }
        this.playerObserver = null;
        this.playerEngine = null;
    }

    private void aEventParam(EventContract eventContract) throws JSONException {
        try {
            if (eventContract.isPlayback()) {
                ViewDataEntity viewData;
                if ((viewData = ((StreamingHub) eventContract).getViewData()) == null) {
                    viewData = new ViewDataEntity();
                }
                viewData.setViewDroppedFrames(this.iDroppedCount);
                ((StreamingHub) eventContract).setViewData(viewData);
            }
            Hub.dispatchEventForPlayer(this.stringCore, eventContract);
        } catch (Throwable throwable) {
            AnalyticsEventLogger.exception(throwable, FP_STATS, "Failed to dispatch event: " + eventContract + ", for player name: " + this.stringCore);
            if (this.customerData != null && this.customerData.getCustomerPlayerData() != null) {
                AnalyticsEventLogger.exception(throwable, FP_STATS, "Failed to dispatch player event: " + eventContract);
            }
        }
    }

    private void aLocal() throws JSONException {
        try {
            WorkSpaceEntity environmentData = new WorkSpaceEntity();
            if (deviceContract != null) {
                this.oFPViewerId = deviceContract.getDeviceId();
                this.pViewerApplicationName = deviceContract.getAppName();
                this.qAppVersion = deviceContract.getAppVersion();
            }
            if (this.oFPViewerId != null) {
                environmentData.setFPViewerId(this.oFPViewerId);
            }
            ViewerDataEntity viewerData = new ViewerDataEntity();
            if (deviceContract != null) {
                viewerData.setViewerOsFamily(deviceContract.getOSFamily());
                viewerData.setViewerOsArchitecture(deviceContract.getHardwareArchitecture());
                viewerData.setViewerOsVersion(deviceContract.getOSVersion());
                viewerData.setViewerDeviceManufacturer(deviceContract.getManufacturer());
                viewerData.setViewerDeviceModel(deviceContract.getModelName());
                viewerData.setViewerDeviceName(deviceContract.getDeviceName());
                viewerData.setViewerDeviceCategory(deviceContract.getDeviceCategory());
                viewerData.setViewerConnectionType(deviceContract.getNetworkConnectionType());
            }
            if (this.pViewerApplicationName != null) {
                viewerData.setViewerApplicationName(this.pViewerApplicationName);
            }
            if (this.qAppVersion != null) {
                viewerData.setViewerApplicationVersion(this.qAppVersion);
            }
            StreamingData streamingData = new StreamingData();
            (streamingData).setEnvironmentData(environmentData);
            streamingData.setViewerData(viewerData);
            Hub.dispatchDataEvent(streamingData);
        } catch (Throwable throwable) {
            AnalyticsEventLogger.exception(throwable, FP_STATS, "Exception caught preparing environment", this.customerData);
        }
    }

    public void setDroppedFramesCount(Long droppedFramesCount) {
        this.iDroppedCount = droppedFramesCount;
    }

    protected long getDroppedFrames() {
        return this.iDroppedCount;
    }

    protected PlayerDataEntity getPlayerData() throws JSONException {
        PlayerDataEntity playerData = new PlayerDataEntity();
        DeviceContract deviceContract1;
        if ((deviceContract1 = getHostDevice()) != null) {
            playerData.setPlayerFPPluginName(deviceContract1.getPluginName());
            playerData.setPlayerFPPluginVersion(deviceContract1.getPluginVersion());
            playerData.setPlayerSoftwareName(deviceContract1.getPlayerSoftware());
        }
        if (deviceContract != null) {
            playerData.setPlayerSoftwareVersion(deviceContract.getPlayerVersion());
        }
        if (this.playerObserver == null) {
            return playerData;
        } else {
            playerData.setPlayerIsPaused(this.playerObserver.isPaused());
            playerData.setPlayerAutoplayOn(this.playerObserver.isAutoPlay());
            playerData.setPlayerPreloadOn(this.isPreLoaded);
            Log.e("playerObserver", this.playerObserver.isPaused() + " " + this.playerObserver.isAutoPlay());
            playerData.setPlayerPlayheadTime(this.playerObserver.getCurrentPosition());
            if (this.playerObserver.getPlayerProgramTime() != null && this.playerObserver.getPlayerProgramTime() != -1L) {
                playerData.setPlayerProgramTime(this.playerObserver.getPlayerProgramTime());
            }
            if (this.playerObserver.getPlayerManifestNewestTime() != null && this.playerObserver.getPlayerManifestNewestTime() != -1L) {
                playerData.setPlayerManifestNewestProgramTime(this.playerObserver.getPlayerManifestNewestTime());
            }
            if (this.jMessage != null) {
                playerData.setPlayerErrorMessage(this.jMessage);
                playerData.setPlayerErrorCode(Integer.toString(this.kExceptionCode));
                playerData.setPlayerErrorContext(this.lContext);
            }
            if (!this.isPlayerSizeChange) {
                this.dPlayerViewWidth = aParamsWithInt(this.playerObserver.getPlayerViewWidth(), 0, 1048576);
                this.ePlayerViewHeight = aParamsWithInt(this.playerObserver.getPlayerViewHeight(), 0, 1048576);
            }
            if (this.mediaPresentation == null) {
                if (this.ePlayerViewHeight != null && this.dPlayerViewWidth != null) {
                    playerData.setPlayerHeight(this.ePlayerViewHeight);
                    playerData.setPlayerWidth(this.dPlayerViewWidth);
                    if (this.gPlayerWidth != null && this.fPlayerHeight != null) {
                        if ((this.gPlayerWidth > this.ePlayerViewHeight || this.fPlayerHeight > this.dPlayerViewWidth) && (this.fPlayerHeight > this.ePlayerViewHeight || this.gPlayerWidth > this.dPlayerViewWidth)) {
                            playerData.setPlayerIsFullscreen("false");
                        } else {
                            playerData.setPlayerIsFullscreen("true");
                        }
                    }
                }
            } else {
                boolean isPresentation = this.mediaPresentation == MediaPresentation.FULLSCREEN;
                playerData.setPlayerIsFullscreen(String.valueOf(isPresentation));
                Log.e("setPlayerIsFullscreen", "if520");
                if (this.ePlayerViewHeight != null && this.dPlayerViewWidth != null) {
                    playerData.setPlayerHeight(this.ePlayerViewHeight);
                    playerData.setPlayerWidth(this.dPlayerViewWidth);
                }
            }
            return playerData;
        }
    }

    private void updateEventsFromLocal() throws JSONException {
        boolean isItTrue = false;
        if (this.playerObserver != null) {
            if (this.playerObserver.getVideoHoldback() != null && this.playerObserver.getVideoHoldback() != -1L) {
                this.videoData.setVideoHoldback(this.playerObserver.getVideoHoldback());
            }
            if (this.playerObserver.getVideoPartHoldback() != null && this.playerObserver.getVideoPartHoldback() != -1L) {
                this.videoData.setVideoPartHoldback(this.playerObserver.getVideoPartHoldback());
            }
            if (this.playerObserver.getVideoPartTargetDuration() != null && this.playerObserver.getVideoPartTargetDuration() != -1L) {
                this.videoData.setVideoPartTargetDuration(this.playerObserver.getVideoPartTargetDuration());
            }
            if (this.playerObserver.getVideoTargetDuration() != null && this.playerObserver.getVideoTargetDuration() != -1L) {
                this.videoData.setVideoTargetDuration(this.playerObserver.getVideoTargetDuration());
            }
            if (this.playerObserver.getSourceWidth() != null && this.videoData.getVideoSourceWidth() != this.playerObserver.getSourceWidth()) {
                this.videoData.setVideoSourceWidth(this.playerObserver.getSourceWidth());
                isItTrue = true;
            }
            if (this.playerObserver.getSourceHeight() != null && this.videoData.getVideoSourceHeight() != this.playerObserver.getSourceHeight()) {
                this.videoData.setVideoSourceHeight(this.playerObserver.getSourceHeight());
                isItTrue = true;
            }
            if (this.playerObserver.getSourceFps() != null && this.videoData.getVideoSourceFPS() != this.playerObserver.getSourceFps()) {
                this.videoData.setVideoSourceFPS(this.playerObserver.getSourceFps());
                isItTrue = true;
            }
            if (this.playerObserver.getSourceHostName() != null && this.videoData.getVideoSourceHostName() != this.playerObserver.getSourceHostName()) {
                this.videoData.setVideoSourceHostName(this.playerObserver.getSourceHostName());
                isItTrue = true;
            }
            if (this.playerObserver.getMimeType() != null && this.videoData.getVideoSourceMimeType() != this.playerObserver.getMimeType()) {
                this.videoData.setVideoSourceMimeType(this.playerObserver.getMimeType());
                isItTrue = true;
            }
            if (this.playerObserver.getSourceDuration() != null && this.videoData.getVideoSourceDuration() != this.playerObserver.getSourceDuration()) {
                this.videoData.setVideoSourceDuration(this.playerObserver.getSourceDuration());
                isItTrue = true;
            }
            if (this.playerObserver.getSourceAdvertisedBitrate() != null && this.videoData.getVideoSourceAdvertisedBitrate() != this.playerObserver.getSourceAdvertisedBitrate()) {
                this.videoData.setVideoSourceAdvertisedBitrate(this.playerObserver.getSourceAdvertisedBitrate());
                isItTrue = true;
            }
            if (this.playerObserver.getPlayerCodec() != null && this.videoData.getVideoSourceCodec() != this.playerObserver.getPlayerCodec()) {
                this.videoData.setVideoSourceCodec(this.playerObserver.getPlayerCodec());
                isItTrue = true;
            }
            if (this.playerObserver.getSourceAdvertisedFramerate() != null && this.videoData.getVideoSourceAdvertisedFramerate() != this.playerObserver.getSourceAdvertisedFramerate()) {
                this.videoData.setVideoSourceAdvertisedFramerate(this.playerObserver.getSourceAdvertisedFramerate());
                isItTrue = true;
            }
            if (isItTrue) {
                StreamingData streamingData = new StreamingData();
                (streamingData).setVideoData(this.videoData);
                this.aEventParam(streamingData);
            }
        }
    }

    public synchronized void handle(EventContract eventContract) throws JSONException {
        String eventType = eventContract.getType();
        PlayerDataEntity playerData = this.getPlayerData();

        // Handle general playback events
        if (!eventContract.isPlayback() && !eventContract.isError()) {
            Log.e("Do not Track", "if");
        } else if (eventContract.isError() && !this.isCallFPStats) {
            Log.e("Do not Track", "else if");
        } else {
            switch (eventType) {
                case "requestCompleted":
                    this.updateEventsFromLocal();
                    MediaStreaming requestCompleted = new MediaStreaming(MediaStreaming.EventType.requestCompleted, this.getPlayerData());
                    requestCompleted.setBandwidthMetricData(((StreamingHub) eventContract).getBandwidthMetricData());
                    this.aEventParam(requestCompleted);
                    break;
                case "requestCanceled":
                    this.updateEventsFromLocal();
                    MediaStreaming requestCanceled = new MediaStreaming(MediaStreaming.EventType.requestCanceled, this.getPlayerData());
                    requestCanceled.setBandwidthMetricData(((StreamingHub) eventContract).getBandwidthMetricData());
                    this.aEventParam(requestCanceled);
                    break;
                case "requestFailed":
                    this.updateEventsFromLocal();
                    MediaStreaming requestFailed = new MediaStreaming(MediaStreaming.EventType.requestFailed, this.getPlayerData());
                    requestFailed.setBandwidthMetricData(((StreamingHub) eventContract).getBandwidthMetricData());
                    this.aEventParam(requestFailed);
                    break;
                case "timeUpdate":
                    this.updateEventsFromLocal();
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.timeUpdate, playerData));
                    break;
                case "play":
                    this.updateEventsFromLocal();
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.play, playerData));
                    break;
                case "playing":
                    this.updateEventsFromLocal();
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.playing, playerData));
                    break;
                case "sampling":
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.sampling, playerData));
                    break;
                case "seeking":
                    this.updateEventsFromLocal();
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.seeking, playerData));
                    break;
                case "seeked":
                    this.updateEventsFromLocal();
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.seeked, playerData));
                    break;
                case "buffering":
                    this.updateEventsFromLocal();
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.buffering, playerData));
                    break;
                case "buffered":
                    this.updateEventsFromLocal();
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.buffered, playerData));
                    break;
                case "pause":
                    this.updateEventsFromLocal();
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.pause, playerData));
                    break;
                case "ended":
                    this.updateEventsFromLocal();
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.ended, playerData));
                    break;
                case "internalError":
                    handleInternalErrorEvent((InternalErrorEvent) eventContract);
                    break;
                case "variantChanged":
                    this.updateEventsFromLocal();
                    this.aEventParam(new MediaStreaming(MediaStreaming.EventType.variantChanged, playerData));
                    break;
                default:
                    break;
            }
            if (this.playerObserver != null) {
                (new Date()).getTime();
                this.playerObserver.getCurrentPosition();
            }
        }
    }

    private void handleInternalErrorEvent(InternalErrorEvent event) throws JSONException {
        this.jMessage = event.getErrorMessage();
        this.kExceptionCode = event.getErrorCode();
        this.lContext = event.getErrorContext();
        this.aEventParam(new MediaStreaming(MediaStreaming.EventType.error, this.getPlayerData()));
    }

    /**
     * Handles ad events by creating the appropriate event instance.
     */
    protected PlayerEngine getCorePlayer() {
        return this.playerEngine;
    }

    private static int aParamsWithInt(int loadVal, int loadVal1, int loadVal2) {
        if (loadVal > 1048576) {
            return 1048576;
        } else {
            return loadVal < 0 ? 0 : loadVal;
        }
    }

    private static class ParamsWithFPStatsA extends TimerTask {
        private final WeakReference<FastPixMetrics> fpStatsWeakReference;
        private final WeakReference<Timer> timerWeakReference;

        public ParamsWithFPStatsA(FastPixMetrics fastPixMetrics, Timer timer) {
            this.fpStatsWeakReference = new WeakReference(fastPixMetrics);
            this.timerWeakReference = new WeakReference(timer);
        }

        public final void run() {
            FastPixMetrics fastPixMetrics;
            if ((fastPixMetrics = this.fpStatsWeakReference.get()) != null) {
                try {
                    if (!fastPixMetrics.playerObserver.isPaused()) {
                        fastPixMetrics.updateEventsFromLocal();
                    }
                } catch (Exception throwable) {
                    try {
                        AnalyticsEventLogger.exception(throwable, FP_STATS, "Exception terminated timer task", fastPixMetrics.customerData);
                    } catch (JSONException ex) {
                        Log.e("JSONException", ex.toString());
                    }
                    try {
                        fastPixMetrics.release();
                    } catch (JSONException ex) {
                        Log.e("JSONException", ex.toString());
                    }
                }
            } else {
                Timer timer1;
                if ((timer1 = this.timerWeakReference.get()) != null) {
                    timer1.cancel();
                    timer1.purge();
                }
            }
        }
    }
}
