package io.fastpix.data.streaming;

import io.fastpix.data.entity.NetworkBandwidthEntity;
import io.fastpix.data.entity.PlayerDataEntity;
import io.fastpix.data.entity.VideoDataEntity;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.Interfaces.PlaybackEventContract;

public class StreamingHub extends AbstractEventContract implements PlaybackEventContract {
    protected PlayerDataEntity playerData;
    protected ViewDataEntity viewData;
    protected VideoDataEntity videoData;
    protected NetworkBandwidthEntity networkBandwidthEntity;
    protected boolean isSuppressed = false;
    public StreamingHub(PlayerDataEntity playerData) {
        this.playerData = playerData;
    }

    public PlayerDataEntity getPlayerData() {
        return this.playerData;
    }

    public boolean isPlayback() {
        return true;
    }

    public void setViewData(ViewDataEntity viewData) {
        this.viewData = viewData;
    }

    public ViewDataEntity getViewData() {
        return this.viewData;
    }

    public void setVideoData(VideoDataEntity videoData) {
        this.videoData = videoData;
    }

    public VideoDataEntity getVideoData() {
        return this.videoData;
    }

    public NetworkBandwidthEntity getBandwidthMetricData() {
        return this.networkBandwidthEntity;
    }

    public void setBandwidthMetricData(NetworkBandwidthEntity networkBandwidthEntity) {
        this.networkBandwidthEntity = networkBandwidthEntity;
    }

    public boolean isSuppressed() {
        return this.isSuppressed;
    }

}

