package io.fastpix.data.streaming;

import io.fastpix.data.entity.PlayerDataEntity;

public class MediaStreaming extends StreamingHub {

    public enum EventType {
        buffered,
        buffering,
        ended,
        error,
        internalHeartbeatEnd,
        internalHeartbeat,
        orientationChange,
        pause,
        playerReady,
        play,
        playing,
        pulse,
        requestCanceled,
        requestCompleted,
        requestFailed,
        sampling,
        seeked,
        seeking,
        timeUpdate,
        variantChanged,
        videoChange,
        viewBegin,
        viewCompleted,
        viewInit,
    }

    private final EventType eventType;

    public MediaStreaming(EventType mEventType, PlayerDataEntity mPlayerData) {
        super(mPlayerData);
        this.eventType = mEventType;

        if (mEventType.equals(EventType.seeking)) {
            this.isSuppressed = true;
        }
    }

    public String getType() {
        return eventType.toString(); // Converts ENUM to lowercase string
    }

    public void setIsSuppressed(boolean isSuppressed) {
        this.isSuppressed = isSuppressed;
    }
}
