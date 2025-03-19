package io.fastpix.data.request;

import org.json.JSONException;
import io.fastpix.data.entity.WorkSpaceEntity;

public class StreamingSession {
    private String string;
    private long aLong;
    private long finalSessionTimeOut;
    private CurrentTime currentTime = new CurrentTime();
    private static final long SESSION_TIMEOUT_MS = 1500000L; // 25 minutes in milliseconds

    public StreamingSession() {
    }

    public void startSession() {
        this.takeSession(this.now());
    }

    private void takeSession(long sesstionLong) {
        this.string = String.valueOf(java.util.UUID.randomUUID());
        this.aLong = sesstionLong;
        this.finalSessionTimeOut = this.aLong + SESSION_TIMEOUT_MS;
    }

    public void setCurrentTime(CurrentTime currentTime) {
        this.currentTime = currentTime;
    }

    public long now() {
        return this.currentTime.now();
    }

    public void updateEnvironmentData(WorkSpaceEntity environmentData) throws JSONException {
        long updatedTime;
        if ((updatedTime = this.now()) > this.finalSessionTimeOut) {
            this.takeSession(this.now());
        } else {
            this.finalSessionTimeOut = updatedTime + SESSION_TIMEOUT_MS;
        }
        environmentData.setSessionStart(this.aLong);
        environmentData.setSessionExpires(this.finalSessionTimeOut);
        environmentData.setSessionId(this.string);
    }
}