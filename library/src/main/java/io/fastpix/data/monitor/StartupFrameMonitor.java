package io.fastpix.data.monitor;

import org.json.JSONException;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.request.AnalyticsEventLogger;
import io.fastpix.data.streaming.SupportStreaming;

public class StartupFrameMonitor extends AbstractMonitor {
    private int playingValue = 0;

    public StartupFrameMonitor(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    private void aTimeTo(long longTime) throws JSONException {
        ViewDataEntity viewData = new ViewDataEntity();
        viewData.setViewTimeToFirstFrame(longTime);
        SupportStreaming viewMetricEvent = new SupportStreaming(SupportStreaming.Type.viewMetricEvent, viewData);
        this.dispatcher.dispatch(viewMetricEvent);
    }

    /**
     * Handles playback events related to request metrics and updates relevant counters and view data.
     *
     * @param streamingHub The playback event to process.
     * @throws JSONException If there is an error handling JSON data.
     */
    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        ViewDataEntity viewData = streamingHub.getViewData();
        if (viewData.getViewProgramChanged()) {
            AnalyticsEventLogger.d("TimeToFirstFrameTracker", "Skipping Time to first frame calculation, this is a program change view");
        } else if (streamingHub.getType().equals("playing") && this.playingValue <= 0) {
            this.playingValue = 1;
            this.aTimeTo(viewData.getViewWatchTime());

        }
    }
}
