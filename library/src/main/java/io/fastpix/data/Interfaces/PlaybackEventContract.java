package io.fastpix.data.Interfaces;

import io.fastpix.data.entity.NetworkBandwidthEntity;
import io.fastpix.data.entity.PlayerDataEntity;
import io.fastpix.data.entity.VideoDataEntity;
import io.fastpix.data.entity.ViewDataEntity;

public interface PlaybackEventContract extends EventContract {
    PlayerDataEntity getPlayerData();

    void setViewData(ViewDataEntity viewData);

    ViewDataEntity getViewData();

    void setVideoData(VideoDataEntity videoData);

    VideoDataEntity getVideoData();

    NetworkBandwidthEntity getBandwidthMetricData();

    void setBandwidthMetricData(NetworkBandwidthEntity networkBandwidthEntity);

    boolean isSuppressed();
}
