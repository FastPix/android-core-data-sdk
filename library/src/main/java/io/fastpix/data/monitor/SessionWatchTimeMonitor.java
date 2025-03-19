package io.fastpix.data.monitor;

import org.json.JSONException;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.streaming.SupportStreaming;

public class SessionWatchTimeMonitor extends AbstractMonitor {
    private long aLong = 0L;
    private long aLong1 = 0L;

    public SessionWatchTimeMonitor(EventEmitter eventEmitter) throws JSONException {
        super(eventEmitter);
        ViewDataEntity viewData = new ViewDataEntity();
        viewData.setViewWatchTime(0L);
        this.dispatch(new SupportStreaming(SupportStreaming.Type.viewMetricEvent,viewData));
    }

    private void wtt(long longTime) throws JSONException {
        if (this.aLong > 0L) {
            this.aLong1 += longTime - this.aLong;
            ViewDataEntity viewData = new ViewDataEntity();
            viewData.setViewWatchTime(this.aLong1);
            this.dispatch(new SupportStreaming(SupportStreaming.Type.viewMetricEvent,viewData));
        }
    }

    /**
     * Handles playback events related to request metrics and updates relevant counters and view data.
     *
     * @param streamingHub The playback event to process.
     * @throws JSONException If there is an error handling JSON data.
     */
    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        String viewType = streamingHub.getType();
        if (viewType.equals("internalHeartbeat")) {
            long viewerTime = streamingHub.getViewData().getViewerTime();
            this.wtt(viewerTime);
            this.aLong = viewerTime;
        } else {
            if (viewType.equals("internalHeartbeatEnd")) {
                this.wtt(streamingHub.getViewData().getViewerTime());
                this.aLong = 0L;
            }
        }
    }
}
