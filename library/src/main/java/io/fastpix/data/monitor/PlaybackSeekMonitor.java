package io.fastpix.data.monitor;

import org.json.JSONException;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.streaming.MediaStreaming;
import io.fastpix.data.streaming.SupportStreaming;

public class PlaybackSeekMonitor extends AbstractMonitor {
    private long viewerTimeLong = 0L;
    private boolean isSeeking = false;
    private int viewSeekCountInt = 0;
    private long viewSeekDuration = 0L;
    private long viewMaxSeekTime = 0L;

    public PlaybackSeekMonitor(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    /**
     * Handles playback events related to request metrics and updates relevant counters and view data.
     *
     * @param streamingHub The playback event to process.
     * @throws JSONException If there is an error handling JSON data.
     */
    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        String type= streamingHub.getType();
        Long viewerTimeLongLocal;
        if (type.equals("seeking")) {
            viewerTimeLongLocal = streamingHub.getViewData().getViewerTime();
            if (!this.isSeeking) {
                this.isSeeking = true;
                MediaStreaming seekingEvent= new MediaStreaming(MediaStreaming.EventType.seeking, streamingHub.getPlayerData());
                seekingEvent.setIsSuppressed(false);
                seekingEvent.setViewData(streamingHub.getViewData());
                this.dispatch(seekingEvent);
            }
            this.viewerTimeLong = viewerTimeLongLocal;
        }
        else if (type.equals("seeked")) {
            viewerTimeLongLocal = streamingHub.getViewData().getViewerTime();
            if (this.viewerTimeLong > 0L) {
                ++this.viewSeekCountInt;
                long longTimeDiff = viewerTimeLongLocal - this.viewerTimeLong;
                this.viewSeekDuration += longTimeDiff;
                if (longTimeDiff > this.viewMaxSeekTime) {
                    this.viewMaxSeekTime = longTimeDiff;
                }
                ViewDataEntity viewData= new ViewDataEntity();
                (viewData ).setViewSeekCount(this.viewSeekCountInt);
                viewData.setViewSeekDuration(this.viewSeekDuration);
                viewData.setViewMaxSeekTime(this.viewMaxSeekTime);
                this.dispatch(new SupportStreaming(SupportStreaming.Type.viewMetricEvent,viewData));
            }
            this.isSeeking = false;
            this.viewerTimeLong = 0L;
        }
        else {
            if (type.equals("viewCompleted")) {
                this.isSeeking = false;
            }
        }
    }
}
