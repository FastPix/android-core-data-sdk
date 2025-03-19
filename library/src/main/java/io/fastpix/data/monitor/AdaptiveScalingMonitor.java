package io.fastpix.data.monitor;

import org.json.JSONException;

import java.util.HashSet;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.PlayerDataEntity;
import io.fastpix.data.entity.VideoDataEntity;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.streaming.SupportStreaming;

public class AdaptiveScalingMonitor extends AbstractMonitor {
    private Long longPlayerPlayHeadTime;
    private Integer integerWidth;
    private Integer integerHeight;
    private Integer integerVideoSourceWidth;
    private Integer integerVideoSourceHeight;
    private final HashSet<String> hashSet = new HashSet();
    private final HashSet<String> hashSet1;
    private long incrementOfChangedTime = 0L;
    private double mathMaxValue = 0.0;
    private double mathMaxValue1 = 0.0;
    private double incrementOfMaxAndChangedValue = 0.0;
    private double incrementOfMaxMindChangedValue1 = 0.0;

    public AdaptiveScalingMonitor(EventEmitter eventEmitter) {
        super(eventEmitter);
        this.hashSet.add("pause");
        this.hashSet.add("buffering");
        this.hashSet.add("seeking");
        this.hashSet.add("timeUpdate");
        this.hashSet.add("viewCompleted");
        this.hashSet.add("error");
        this.hashSet1 = new HashSet();
        this.hashSet1.add("playing");
        this.hashSet1.add("timeUpdate");
    }

    /**
     * Handles playback events related to request metrics and updates relevant counters and view data.
     *
     * @param streamingHub The playback event to process.
     * @throws JSONException If there is an error handling JSON data.
     */
    @Override
    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        super.handlePlaybackEvent(streamingHub);
        Long updateTime;
        if (this.hashSet.contains(streamingHub.getType()) && (updateTime = streamingHub.getPlayerData().getPlayerPlayheadTime()) != null) {
            long localUpdateTime = updateTime;
            if (this.longPlayerPlayHeadTime != null && this.integerWidth != null
                    && this.integerHeight != null && this.integerVideoSourceWidth != null &&
                    this.integerVideoSourceHeight != null && this.integerWidth > 0 &&
                    this.integerHeight > 0 && this.integerVideoSourceWidth > 0 && this.integerVideoSourceHeight > 0) {
                CallToChangeTime(localUpdateTime);
            }
            this.longPlayerPlayHeadTime = null;
        }
        if (this.hashSet1.contains(streamingHub.getType())) {
            PlayerDataEntity playerData = streamingHub.getPlayerData();
            this.longPlayerPlayHeadTime = playerData.getPlayerPlayheadTime();
            this.integerWidth = playerData.getPlayerWidth();
            this.integerHeight = playerData.getPlayerHeight();
            VideoDataEntity videoData = streamingHub.getVideoData();
            this.integerVideoSourceWidth = videoData.getVideoSourceWidth();
            this.integerVideoSourceHeight = videoData.getVideoSourceHeight();
        }
    }

    private void CallToChangeTime(long localUpdateTime) throws JSONException {
        long changedTime;
        if ((changedTime = localUpdateTime - this.longPlayerPlayHeadTime) >= 0L) {
            double widthOfSourceWidth = (double) this.integerWidth / (double) this.integerVideoSourceWidth;
            double heightOfSourceHeight = (double) this.integerHeight / (double) this.integerVideoSourceHeight;
            double minDifferenceFeatherweight = Math.min(widthOfSourceWidth, heightOfSourceHeight);
            double maxCalculationOfValue = Math.max(0.0, minDifferenceFeatherweight - 1.0);
            double calumniationOfValue1 = Math.max(0.0, 1.0 - minDifferenceFeatherweight);
            this.mathMaxValue = Math.max(this.mathMaxValue, maxCalculationOfValue);
            this.mathMaxValue1 = Math.max(this.mathMaxValue1, calumniationOfValue1);
            this.incrementOfChangedTime += changedTime;
            this.incrementOfMaxAndChangedValue += maxCalculationOfValue * changedTime;
            this.incrementOfMaxMindChangedValue1 += calumniationOfValue1 * changedTime;
            ViewDataEntity viewData = new ViewDataEntity();
            viewData.setViewMaxUpscalePercentage(this.mathMaxValue);
            viewData.setViewMaxDownscalePercentage(this.mathMaxValue1);
            viewData.setViewTotalContentPlaybackTime(this.incrementOfChangedTime);
            viewData.setViewTotalUpscaling(this.incrementOfMaxAndChangedValue);
            viewData.setViewTotalDownscaling(this.incrementOfMaxMindChangedValue1);
            this.dispatch(new SupportStreaming(SupportStreaming.Type.viewMetricEvent,viewData));
        }
    }
}
