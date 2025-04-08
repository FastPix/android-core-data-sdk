package io.fastpix.data.request;

import org.json.JSONException;

import io.fastpix.data.streaming.EventHandler;
import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.streaming.UserSessionEventContract;
import io.fastpix.data.streaming.MonitoredEventContract;
import io.fastpix.data.streaming.StreamingData;
import io.fastpix.data.Interfaces.PlaybackEventContract;
import io.fastpix.data.entity.CustomDataEntity;
import io.fastpix.data.entity.CustomerVideoDataEntity;
import io.fastpix.data.entity.CustomerViewDataEntity;
import io.fastpix.data.entity.CustomerViewerDataEntity;
import io.fastpix.data.entity.VideoDataEntity;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.monitor.RemoteEventMonitor;
import io.fastpix.data.monitor.RemoteHeartbeatMonitor;
import io.fastpix.data.monitor.LocalHeartbeatMonitor;
import io.fastpix.data.monitor.DelayedResumeMonitor;
import io.fastpix.data.monitor.MediaPlaybackMonitor;
import io.fastpix.data.monitor.BufferingEventMonitor;
import io.fastpix.data.monitor.RequestMetricsMonitor;
import io.fastpix.data.monitor.AdaptiveScalingMonitor;
import io.fastpix.data.monitor.PlaybackSeekMonitor;
import io.fastpix.data.monitor.StartupFrameMonitor;
import io.fastpix.data.monitor.PlaybackStateMonitor;
import io.fastpix.data.monitor.ViewerTimeMonitor;
import io.fastpix.data.monitor.SessionWatchTimeMonitor;
import io.fastpix.data.streaming.SupportStreaming;

/**
 * CoreView class that extends EventBus to handle the dispatching of events.
 * It serves as the core view for interacting with the player and listens for
 * various events to update its state or trigger necessary actions.
 */
public class ViewEngine extends EventHandler {
    private ViewDataEntity viewData;
    private VideoDataEntity videoData;
    private CustomerVideoDataEntity customerVideoData;
    private CustomerViewDataEntity customerViewData;
    private CustomerViewerDataEntity customerViewerData;
    private CustomDataEntity customData = new CustomDataEntity();
    private int viewSequenceNumberInt;

    public ViewEngine() {
    }

    /**
     * Gets the video data associated with the current view.
     *
     * @return The VideoData object containing video-specific information.
     */
    public VideoDataEntity getVideoData() {
        return this.videoData;
    }

    /**
     * Gets the customer-specific video data associated with the current view.
     *
     * @return The CustomerVideoData object containing customer-specific video details.
     */
    public CustomerVideoDataEntity getCustomerVideoData() {
        return this.customerVideoData;
    }

    /**
     * Gets the custom data associated with the current view.
     *
     * @return The CustomData object containing any custom data related to the view.
     */
    public CustomDataEntity getCustomData() {
        return this.customData;
    }

    /**
     * Gets the customer-specific view data associated with the current view.
     *
     * @return The CustomerViewData object containing customer-specific view information.
     */
    public CustomerViewDataEntity getCustomerViewData() {
        return this.customerViewData;
    }

    /**
     * Gets the customer viewer data associated with the current view.
     *
     * @return The CustomerViewerData object containing customer viewer-related information.
     */
    public CustomerViewerDataEntity getCustomerViewerData() {
        return this.customerViewerData;
    }

    /**
     * Gets the general view data associated with the current view.
     *
     * @return The ViewData object containing general view information.
     */
    public ViewDataEntity getViewData() {
        return this.viewData;
    }


    /**
     * Dispatches an event to the appropriate listeners and processes it based on its type.
     * The method ensures that trackable events, error events, playback events, and data events
     * are handled and dispatched correctly to the appropriate listeners or the core view.
     *
     * @param eventContract The event to be dispatched.
     * @throws JSONException If there is an issue processing the event (e.g., invalid or missing data).
     */
    public void dispatch(EventContract eventContract) throws JSONException {
        if (eventContract.isTrackable()) {
            MonitoredEventContract monitoredEvent = (MonitoredEventContract) eventContract;
            this.viewData.update(monitoredEvent.getViewData());
            this.viewData.setViewSequenceNumber(++this.viewSequenceNumberInt);
            monitoredEvent.setViewData(this.viewData);
            monitoredEvent.setVideoData(this.videoData);
            monitoredEvent.setCustomerVideoData(this.customerVideoData);
            monitoredEvent.setCustomerViewData(this.customerViewData);
            monitoredEvent.setCustomerViewerData(this.customerViewerData);
            monitoredEvent.setCustomData(this.customData);
        } else if (eventContract.isPlayback()) {
            PlaybackEventContract iPlaybackEvent = (PlaybackEventContract) eventContract;
            if (iPlaybackEvent.getType().equals("viewInit")) {
                this.viewData = new ViewDataEntity();
                this.viewData.setViewId(String.valueOf(java.util.UUID.randomUUID()));
                this.viewData.setViewStart(System.currentTimeMillis());
                this.videoData = new VideoDataEntity();
                this.customerVideoData = new CustomerVideoDataEntity();
                this.customerViewData = new CustomerViewDataEntity();
                this.customerViewerData = new CustomerViewerDataEntity();
                this.viewSequenceNumberInt = 0;
                this.addListener(new PlaybackStateMonitor(this));
                this.addListener(new DelayedResumeMonitor(this));
                this.addListener(new ViewerTimeMonitor(this));
                this.addListener(new AdaptiveScalingMonitor(this));
                this.addListener(new PlaybackSeekMonitor(this));
                this.addListener(new LocalHeartbeatMonitor(this));
                this.addListener(new MediaPlaybackMonitor(this));
                this.addListener(new SessionWatchTimeMonitor(this));
                this.addListener(new StartupFrameMonitor(this));
                this.addListener(new BufferingEventMonitor(this));
                this.addListener(new RequestMetricsMonitor(this));
                this.addListener(new RemoteEventMonitor(this));
                this.addListener(new RemoteHeartbeatMonitor(this));
            }
            if (iPlaybackEvent.getViewData() != null) {
                this.viewData.update(iPlaybackEvent.getViewData());
            }
            iPlaybackEvent.setViewData(this.viewData);
            iPlaybackEvent.setVideoData(this.videoData);
        }

        if (eventContract.isViewMetric()) {
            this.viewData.update(((SupportStreaming) eventContract).getViewData());
        } else if (eventContract.isSessionData()) {
            UserSessionEventContract userSessionEvent = (UserSessionEventContract) eventContract;
            Helper.let(userSessionEvent.getViewData(), viewDataCV -> this.viewData.update(viewDataCV));
            Helper.let(userSessionEvent.getVideoData(), videoDataCV -> this.videoData.update(videoDataCV));
            Helper.let(userSessionEvent.getCustomerVideoData(), customerVideoDataCV -> this.customerVideoData.update(customerVideoDataCV));
            Helper.let(userSessionEvent.getCustomerViewData(), customerViewDataCV -> this.customerViewData.update(customerViewDataCV));
            Helper.let(userSessionEvent.getCustomerViewerData(), customerViewerDataCV -> this.customerViewerData.update(customerViewerDataCV));
            Helper.let(userSessionEvent.getCustomData(), customDataCV -> this.customData.update(customDataCV));
        } else {
            if (eventContract.isData()) {
                StreamingData streamingData = (StreamingData) eventContract;
                if (this.videoData != null && this.customerVideoData != null) {
                    this.videoData.update(streamingData.getVideoData());
                    this.customerVideoData.update(streamingData.getCustomerVideoData());
                    this.customerViewData.update(streamingData.getCustomerViewData());
                    this.customerViewerData.update(streamingData.getCustomerViewerData());
                    this.customData.update(streamingData.getCustomData());
                }
            } else {
                super.dispatch(eventContract);
            }
        }
    }
}
