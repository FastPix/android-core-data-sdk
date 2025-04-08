package io.fastpix.data.monitor;

import org.json.JSONException;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.streaming.MediaStreaming;
public class PlaybackStateMonitor extends AbstractMonitor {
    private PlaybackState playbackState;

    public PlaybackStateMonitor(EventEmitter dispatcher) {
        super(dispatcher);
        this.playbackState = PlaybackState.INITIALIZED;
    }

    protected void handlePlaybackEvent(StreamingHub event) throws JSONException {
        String eventType = event.getType();

        if (PlaybackEventTypes.VIEW_INIT.equals(eventType)) {
            playbackState = PlaybackState.READY;
        } else {
            if (!PlaybackEventTypes.PLAY.equals(eventType)) {
                return;
            }
            if (playbackState == PlaybackState.READY) {
                playbackState = PlaybackState.PLAYING;
                dispatch(new MediaStreaming(MediaStreaming.EventType.viewBegin,event.getPlayerData()));
            }
        }
    }

    private enum PlaybackState {
        INITIALIZED,
        READY,
        PLAYING
    }

    private static class PlaybackEventTypes {
        static final String VIEW_INIT = "viewInit";
        static final String PLAY = "play";
    }
}

