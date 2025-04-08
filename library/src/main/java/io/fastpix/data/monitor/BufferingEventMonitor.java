package io.fastpix.data.monitor;

import org.json.JSONException;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.streaming.SupportStreaming;

/**
 * RebufferTracker is responsible for tracking rebuffering events during video playback.
 * It extends BaseAdTracker, inheriting the functionality for tracking ad-related events.
 */
public class BufferingEventMonitor extends AbstractMonitor {
    private boolean isBuffering = false;
    private int viewRebufferCountInt = 0;
    private double viewWatchTimeDouble = 0.0;
    private long viewRebufferDurationLong = 0L;
    private Long viewerTimeLong1 = 0L;

    // Constructor for the RebufferTracker class, which extends a superclass (possibly an EventDispatcher).
    public BufferingEventMonitor(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    /**
     * Tracks rebuffering events during playback and dispatches related metrics.
     *
     * @param streamingHub The playback event that contains data to process.
     * @throws JSONException If there is an error handling JSON data.
     */
    private void aPlayback(StreamingHub streamingHub) throws JSONException {
        ViewDataEntity viewData = new ViewDataEntity();
        Long aLong2;
        if ((aLong2 = streamingHub.getViewData().getViewerTime()) != null && this.viewerTimeLong1 != null && aLong2 - this.viewerTimeLong1 > 0L && this.isBuffering) {
            this.viewRebufferDurationLong += aLong2 - this.viewerTimeLong1;
            this.viewerTimeLong1 = aLong2;
        }
        viewData.setViewRebufferCount(this.viewRebufferCountInt);
        viewData.setViewRebufferDuration(this.viewRebufferDurationLong);
        if (streamingHub.getViewData().getViewWatchTime() != null && streamingHub.getViewData().getViewWatchTime() > 0L) {
            double viewRebufferFrequencyDouble1 = this.viewRebufferCountInt / this.viewWatchTimeDouble;
            double viewRebufferPercentageDouble2 = this.viewRebufferDurationLong / this.viewWatchTimeDouble;
            viewData.setViewRebufferFrequency(viewRebufferFrequencyDouble1);
            viewData.setViewRebufferPercentage(viewRebufferPercentageDouble2);
        }
        this.dispatch(new SupportStreaming(SupportStreaming.Type.viewMetricEvent,viewData));
    }

    /**
     * Handles playback events and tracks rebuffering metrics.
     *
     * @param streamingHub The playback event to process.
     * @throws JSONException If there is an error handling JSON data.
     */
    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        String type = streamingHub.getType();
        if (streamingHub.getViewData() != null && streamingHub.getViewData().getViewWatchTime() != null)
            this.viewWatchTimeDouble = streamingHub.getViewData().getViewWatchTime();

        if (type.equals("buffering")) {
            if (!this.isBuffering) {
                this.isBuffering = true;
                ++this.viewRebufferCountInt;
                if (streamingHub.getViewData().getViewerTime() != null) {
                    this.viewerTimeLong1 = streamingHub.getViewData().getViewerTime();
                }
                this.aPlayback(streamingHub);
            }
        } else {
            if (type.equals("buffered")) {
                this.aPlayback(streamingHub);
                this.isBuffering = false;
                return;
            }
            if (type.equals("internalHeartbeat") || type.equals("internalHeartbeatEnd")) {
                this.aPlayback(streamingHub);
            }
        }
    }
}
