package io.fastpix.data.monitor;

import org.json.JSONException;

import java.util.HashSet;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.request.CurrentTime;
import io.fastpix.data.Interfaces.TimeMonitor;
import io.fastpix.data.streaming.SupportStreaming;

public class ViewerTimeMonitor extends AbstractMonitor implements TimeMonitor {
    private final HashSet<String> hashSet = new HashSet();
    private CurrentTime currentTime;

    public ViewerTimeMonitor(EventEmitter eventEmitter) {
        super(eventEmitter);
        this.hashSet.add("internalHeartbeat");
        this.hashSet.add("internalHeartbeatEnd");
        this.setCurrentTime(new CurrentTime());
    }

    public void setCurrentTime(CurrentTime currentTime) {
        this.currentTime = currentTime;
    }

    public long now() {
        return this.currentTime.now();
    }

    /**
     * Handles playback events related to request metrics and updates relevant counters and view data.
     *
     * @param streamingHub The playback event to process.
     * @throws JSONException If there is an error handling JSON data.
     */
    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        if (!this.hashSet.contains(streamingHub.getType())) {
            ViewDataEntity viewData= new ViewDataEntity();
            viewData.setViewerTime(this.now());
            this.dispatch(new SupportStreaming(SupportStreaming.Type.viewMetricEvent,viewData));
        }
    }
}
