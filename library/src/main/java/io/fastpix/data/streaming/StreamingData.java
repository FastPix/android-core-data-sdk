package io.fastpix.data.streaming;

import io.fastpix.data.entity.CustomDataEntity;
import io.fastpix.data.entity.CustomerPlayerDataEntity;
import io.fastpix.data.entity.CustomerVideoDataEntity;
import io.fastpix.data.entity.CustomerViewDataEntity;
import io.fastpix.data.entity.CustomerViewerDataEntity;
import io.fastpix.data.entity.WorkSpaceEntity;
import io.fastpix.data.entity.VideoDataEntity;
import io.fastpix.data.entity.ViewerDataEntity;

public class StreamingData extends AbstractEventContract {
    public static final String TYPE = "dataEvent";//Don't change
    private ViewerDataEntity viewerData;
    private WorkSpaceEntity environmentData;
    private VideoDataEntity videoData;
    private CustomerVideoDataEntity customerVideoData;
    private CustomerViewDataEntity customerViewData;
    private CustomerPlayerDataEntity customerPlayerData;
    private CustomerViewerDataEntity customerViewerData;
    private CustomDataEntity customData;

    public StreamingData() {
    }

    public boolean isData() {
        return true;
    }

    public String getType() {
        return TYPE;
    }

    public ViewerDataEntity getViewerData() {
        return this.viewerData;
    }

    public void setViewerData(ViewerDataEntity viewerData) {
        this.viewerData = viewerData;
    }

    public WorkSpaceEntity getEnvironmentData() {
        return this.environmentData;
    }

    public void setEnvironmentData(WorkSpaceEntity environmentData) {
        this.environmentData = environmentData;
    }

    public CustomerVideoDataEntity getCustomerVideoData() {
        return this.customerVideoData;
    }

    public void setCustomerVideoData(CustomerVideoDataEntity customerVideoData) {
        this.customerVideoData = customerVideoData;
    }

    public CustomerViewDataEntity getCustomerViewData() {
        return this.customerViewData;
    }

    public void setCustomerViewData(CustomerViewDataEntity customerViewData) {
        this.customerViewData = customerViewData;
    }

    public CustomerPlayerDataEntity getCustomerPlayerData() {
        return this.customerPlayerData;
    }

    public void setCustomerPlayerData(CustomerPlayerDataEntity customerPlayerData) {
        this.customerPlayerData = customerPlayerData;
    }

    public VideoDataEntity getVideoData() {
        return this.videoData;
    }

    public void setVideoData(VideoDataEntity videoData) {
        this.videoData = videoData;
    }

    public void setCustomData(CustomDataEntity customData) {
        this.customData = customData;
    }

    public CustomDataEntity getCustomData() {
        return this.customData;
    }

    public CustomerViewerDataEntity getCustomerViewerData() {
        return this.customerViewerData;
    }

    public void setCustomerViewerData(CustomerViewerDataEntity customerViewerData) {
        this.customerViewerData = customerViewerData;
    }

}

