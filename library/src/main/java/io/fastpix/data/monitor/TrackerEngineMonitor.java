package io.fastpix.data.monitor;

final class TrackerEngineMonitor {
    final int trackIdInt;
    final int trackCodeInt;
    static final TrackerEngineMonitor TRACKER_CORE_A = new TrackerEngineMonitor(10000, 300);
    static final TrackerEngineMonitor TRACKER_CORE_A1 = new TrackerEngineMonitor(30000, 600);

    private TrackerEngineMonitor(int trackId, int trackCode) {
        this.trackIdInt = trackId;
        this.trackCodeInt = trackCode;
    }

    static TrackerEngineMonitor a(int trackId) {
        int trackId1 = trackId;
        if (trackId < 10000) {
            trackId1 = 10000;
        } else if (trackId > 60000) {
            trackId1 = 60000;
        }
        if ((trackId = trackId / 1000 * 30) < 300) {
            trackId = 300;
        } else if (trackId > 1000) {
            trackId = 1000;
        }
        return new TrackerEngineMonitor(trackId1, trackId);
    }
}
