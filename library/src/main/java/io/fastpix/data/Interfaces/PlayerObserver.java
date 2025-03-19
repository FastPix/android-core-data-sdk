package io.fastpix.data.Interfaces;

public interface PlayerObserver {
    long getCurrentPosition();

    String getMimeType();

    Integer getSourceWidth();

    Integer getSourceHeight();

    Integer getSourceFps();

    Integer getSourceAdvertisedBitrate();

    Float getSourceAdvertisedFramerate();

    Long getSourceDuration();

    boolean isPaused();

    boolean isBuffering();

    int getPlayerViewWidth();

    int getPlayerViewHeight();

    String getPlayerCodec();

    String getSourceHostName();

    Long getPlayerProgramTime();

    Long getPlayerManifestNewestTime();

    Long getVideoHoldback();

    Long getVideoPartHoldback();

    Long getVideoPartTargetDuration();

    Long getVideoTargetDuration();
}
