package io.fastpix.data.request;

import java.util.Date;

public class CurrentTime {
    private long aLong;
    private long aLong1;
    private final boolean aBoolean;

    public CurrentTime() {
        if (FastPixMetrics.getHostDevice() != null) {
            this.aLong = (new Date()).getTime();
            this.aLong1 = FastPixMetrics.getHostDevice().getElapsedRealtime();
            this.aBoolean = true;
        } else {
            this.aBoolean = false;
        }
    }

    public long now() {
        return this.aBoolean ? this.aLong + (FastPixMetrics.getHostDevice().getElapsedRealtime() - this.aLong1) : (new Date()).getTime();
    }
}
