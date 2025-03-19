package io.fastpix.data.monitor;

import org.json.JSONException;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.request.CurrentTime;
import io.fastpix.data.request.AnalyticsEventLogger;
import io.fastpix.data.streaming.SupportStreaming;

/**
 * PlaybackTimeTracker is responsible for tracking playback time-related events.
 * It extends BaseTracker, inheriting its core tracking functionalities.
 */
public class MediaPlaybackMonitor extends AbstractMonitor {
    private long aLong = -1L;
    private long aLong1 = 0L;
    private long aLong2 = -1L;
    private long aLong3 = -1L;
    private final CurrentTime currentTime;


    /**
     * Constructor for PlaybackTimeTracker.
     *
     * @param eventEmitter The event dispatcher responsible for handling and dispatching events.
     */
    public MediaPlaybackMonitor(EventEmitter eventEmitter) {
        super(eventEmitter);
        this.currentTime = new CurrentTime();
    }

    /**
     * Constructor for PlaybackTimeTracker.
     *
     * @param eventEmitter The event dispatcher responsible for handling and dispatching playback events.
     * @param currentTime      The CurrentTime instance used to track playback timestamps.
     */
    public MediaPlaybackMonitor(EventEmitter eventEmitter, CurrentTime currentTime) {
        super(eventEmitter);
        this.currentTime = currentTime;
    }

    /**
     * Tracks playback time and dispatches view metrics.
     *
     * @param longTime The current playback position in milliseconds.
     * @throws JSONException If there is an error handling JSON data.
     */
    private void aForTime(long longTime) throws JSONException {
        long nowCurrentTiem = this.currentTime.now();
        long changedTime = longTime - this.aLong;
        if (this.aLong >= 0L && longTime > this.aLong) {
            if (changedTime <= 1000L) {
                this.aLong1 += changedTime;
                ViewDataEntity viewData = new ViewDataEntity();
                viewData.setViewContentPlaybackTime(this.aLong1);
                if (this.aLong2 > -1L) {
                    viewData.setViewMaxPlayheadPosition(this.aLong2);
                }
                this.dispatch(new SupportStreaming(SupportStreaming.Type.viewMetricEvent, viewData));
            } else {
                AnalyticsEventLogger.d("PlaybackTimeTracker", "Playhead position jump of over 1 seconds detected.");
            }
        }
        this.aLong3 = nowCurrentTiem;
        this.aLong = longTime;
    }

    /**
     * Handles different types of playback events and updates playback tracking.
     *
     * @param streamingHub The playback event to process.
     * @throws JSONException If there is an error handling JSON data.
     */
    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        Long timeOf;
        if ((timeOf = streamingHub.getPlayerData().getPlayerPlayheadTime()) != null) {
            String type = streamingHub.getType();
            if (timeOf > this.aLong2) {
                this.aLong2 = timeOf;
            }
            if (type.equals("internalHeartbeat")) {
                this.aForTime(timeOf);
            } else if (!type.equals("internalHeartbeatEnd") && !type.equals("seeking")) {
                if (type.equals("seeked")) {
                    this.aLong = timeOf;
                }
            } else {
                long timeOf1 = timeOf;
                this.aForTime(timeOf1);
                this.aLong = -1L;
                this.aLong3 = -1L;
            }
        }
    }
}
