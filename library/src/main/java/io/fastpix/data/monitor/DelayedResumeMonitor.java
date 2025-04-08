package io.fastpix.data.monitor;

import android.util.Log;
import io.fastpix.data.request.ViewEngine;
import io.fastpix.data.streaming.StreamingData;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.request.CurrentTime;
import io.fastpix.data.request.AnalyticsEventLogger;
import io.fastpix.data.streaming.MediaStreaming;

import org.json.JSONException;
import java.util.concurrent.TimeUnit;

/**
 * LongResumeTracker is responsible for tracking events related to long resume actions
 * within a playback system. It extends from the `BaseTracker` class and is specialized
 * in managing scenarios where playback is resumed after being paused for a long period.
 * This tracker class captures and processes the resume event, ensuring that the system
 * appropriately handles long pause and resume transitions.
 *
 * The `LongResumeTracker` works in conjunction with playback events to provide accurate
 * tracking of the user’s playback behavior, such as when a user resumes playback after
 * an extended pause.
 */
public class DelayedResumeMonitor extends AbstractMonitor {
    private static final long A_TIME;
    private final CurrentTime currentTime;
    private final ViewEngine viewEngine;
    private long aLong;
    private boolean aBoolean;

    /**
     * Constructor for LongResumeTracker.
     *
     * Initializes the LongResumeTracker with the provided CoreView and CurrentTime objects.
     * This constructor allows the tracker to interact with the core view of the playback system
     * and access the current system time, which may be used for tracking long pause and resume events.
     * The constructor delegates the initialization of the tracker to another constructor, passing in the
     * provided `coreView` and a new instance of `CurrentTime` to handle time-based functionality.
     *
     * @param viewEngine The core view of the playback system that this tracker will interact with.
     */
    public DelayedResumeMonitor(ViewEngine viewEngine) {
        this(viewEngine, new CurrentTime());
    }

    /**
     * Constructor for LongResumeTracker with CoreView and CurrentTime.
     *
     * Initializes the LongResumeTracker with the provided `CoreView` and `CurrentTime` objects.
     * The constructor calls the parent class (`BaseTracker`) constructor to initialize the core view,
     * and then initializes the `currentTime` and `coreView` fields for use within the tracker.
     * The current time is recorded at the time of construction to track time-based events, such as
     * long pause and resume actions.
     *
     * @param viewEngine The core view of the playback system, used for interacting with the UI or playback context.
     * @param currentTime The current time object used to obtain the system's current time.
     */
    private DelayedResumeMonitor(ViewEngine viewEngine, CurrentTime currentTime) {
        super(viewEngine);
        this.currentTime = currentTime;
        this.viewEngine = viewEngine;
        this.aLong = currentTime.now();
    }

    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        switch (streamingHub.getType()) {
            case "internalHeartbeatEnd":
                this.aBoolean = false;
                return;
            case "internalHeartbeat":
                this.aBoolean = true;
                return;
            default:
                break;
        }
        long timeofthis = this.currentTime.now();
        if (timeofthis - this.aLong >= A_TIME && !streamingHub.getType().equals("viewInit")) {
            AnalyticsEventLogger.d("LongResumeTracker", "Event handled after " + A_TIME + "ms. That's a new view");
            StreamingData streamingData = new StreamingData();
            (streamingData).setVideoData(this.viewEngine.getVideoData());
            streamingData.setCustomerVideoData(this.viewEngine.getCustomerVideoData());
            streamingData.setCustomData(this.viewEngine.getCustomData());
            streamingData.setCustomerViewData(this.viewEngine.getCustomerViewData());
            this.dispatch(new MediaStreaming(MediaStreaming.EventType.viewInit, streamingHub.getPlayerData()));
            this.dispatch(streamingData);
            ViewDataEntity viewData = this.viewEngine.getViewData();
            streamingHub.setViewData(viewData);
            if (this.aBoolean && (!"play".equals(streamingHub.getType()) )) {
                MediaStreaming playEvent = new MediaStreaming(MediaStreaming.EventType.play, streamingHub.getPlayerData());
                playEvent.setViewData(viewData);
                this.viewEngine.dispatch(playEvent);
                Log.e("playevent", playEvent.toString());
                if (!"playing".equals(streamingHub.getType())) {
                    MediaStreaming playingEvent = new MediaStreaming(MediaStreaming.EventType.playing, streamingHub.getPlayerData());
                    playingEvent.setViewData(viewData);
                    this.viewEngine.dispatch(playingEvent);
                }
            }
        }
        this.aLong = timeofthis;
    }

    static {
        A_TIME = TimeUnit.HOURS.toMillis(1L);
    }
}
