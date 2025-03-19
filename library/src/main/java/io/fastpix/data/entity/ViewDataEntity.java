package io.fastpix.data.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ViewData class extends BaseQueryData to inherit common query-related functionalities.
 * This class can be used to hold and manage data specific to view-related operations.
 */
public class ViewDataEntity extends QueryDataEntity {
    // Total count of view requests made
    public static final String VIEW_REQUEST_COUNT = "verqco";

    // Total count of failed view requests
    public static final String VIEW_REQUEST_FAILED_COUNT = "verqfaco";

    // Total count of canceled view requests
    public static final String VIEW_REQUEST_CANCELED_COUNT = "verqclco";

    // Total count of preroll ad requests
    public static final String VIEW_PREROLL_REQUEST_COUNT = "veperqco";

    // Preroll ad ID
    public static final String VIEW_PREROLL_AD_ID = "vepeadid";

    // Preroll creative ID
    public static final String VIEW_PREROLL_CREATIVE_ID = "vepecrid";

    // Preroll ad requested count
    public static final String VIEW_PREROLL_REQUESTED = "veperd";

    // Preroll ad played count
    public static final String VIEW_PREROLL_PLAYED = "vepepy";

    // Total time taken for preroll ad requests
    public static final String VIEW_PREROLL_REQUEST_TIME = "veperqti";

    // Startup preroll request time
    public static final String VIEW_STARTUP_PREROLL_REQUEST_TIME = "vespperqti";

    // Time taken for preroll ad to load
    public static final String VIEW_PREROLL_LOAD_TIME = "vepeloti";

    // Time taken for startup preroll ad to load
    public static final String VIEW_STARTUP_PREROLL_LOAD_TIME = "vesppeoti";

    // End time of the view
    public static final String VIEW_END_TIME = "veedti";

    // Unique identifier for the view
    public static final String VIEW_ID = "veid";

    // Maximum percentage of downscaling applied during view
    public static final String VIEW_MAX_DOWNSCALE_PERCENTAGE = "vemadopg";

    // Maximum seek time during the view
    public static final String VIEW_MAX_SEEK_TIME = "vemaseti";

    // Maximum percentage of upscaling applied during view
    public static final String VIEW_MAX_UPSCALE_PERCENTAGE = "vemauppg";

    // Time to first ad during the view (mid-roll)
    public static final String VIEW_MIDROLL_TIME_TO_FIRST_AD = "vemltitofiad";

    // Percentage of content viewed during the session
    public static final String VIEW_PERCENTAGE_VIEWED = "vepgvw";

    // Total count of rebuffer events during the view
    public static final String VIEW_REBUFFER_COUNT = "verbco";

    // Total duration of rebuffer events during the view
    public static final String VIEW_REBUFFER_DURATION = "verbdu";

    // Frequency of rebuffer events during the view
    public static final String VIEW_REBUFFER_FREQUENCY = "verbfq";

    // Percentage of the view time spent on rebuffering
    public static final String VIEW_REBUFFER_PERCENTAGE = "verbpg";

    // Total count of seeks during the view
    public static final String VIEW_SEEK_COUNT = "veseco";

    // Total duration of seeks during the view
    public static final String VIEW_SEEK_DURATION = "vesedu";

    // Sequence number of the view
    public static final String VIEW_SEQUENCE_NUMBER = "vesqnu";

    // The start time of the view
    public static final String VIEW_START = "vest";

    // Time to first frame displayed in the view
    public static final String VIEW_TIME_TO_FIRST_FRAME = "vetitofifr";

    // Time to load the preroll ad
    public static final String VIEW_TIME_TO_PREROLL = "vetitope";

    // Content playback time during the view
    public static final String VIEW_CONTENT_PLAYBACK_TIME = "vectpbti";

    // Total content playback time during the session
    public static final String VIEW_TOTAL_CONTENT_PLAYBACK_TIME = "vetlctpbti";

    // Total time spent downscaling the content during the view
    public static final String VIEW_TOTAL_DOWNSCALING = "vetldg";

    // Total time spent upscaling the content during the view
    public static final String VIEW_TOTAL_UPSCALING = "vetlug";

    // Count of waiting rebuffer events
    public static final String VIEW_WAITING_REBUFFER_COUNT = "vewgrbco";

    // Duration of waiting rebuffer events
    public static final String VIEW_WAITING_REBUFFER_DURATION = "vewgrbdu";

    // Total watch time during the view session
    public static final String VIEW_WATCH_TIME = "vewati";

    // Viewer time for the session
    public static final String VIEWER_TIME = "vitp";

    // Minimum request throughput during the view
    public static final String VIEW_MIN_REQUEST_THROUGHPUT = "vemnrqth";

    // Average request throughput during the view
    public static final String VIEW_AVERAGE_REQUEST_THROUGHPUT = "veavrqth";

    // Maximum request latency during the view
    public static final String VIEW_MAX_REQUEST_LATENCY = "vemarqlt";

    // Average request latency during the view
    public static final String VIEW_AVERAGE_REQUEST_LATENCY = "veavrqlt";

    // Device orientation during the view
    public static final String VIEW_DEVICE_ORIENTATION = "vedeor";

    // Total count of dropped frames during the view
    public static final String VIEW_DROPPED_FRAMES = "vedrfrco";

    // Count of program changes during the view
    public static final String VIEW_PROGRAM_CHANGED = "vepmch";

    // Maximum position of the playhead during the view
    public static final String VIEW_MAX_PLAYHEAD_POSITION = "vemaphps";

    // Type of DRM applied during the view
    public static final String VIEW_DRM_TYPE = "vedrty";


//    public static final List<String> keysViewData;

    public static final List<String> keysViewData = Collections.unmodifiableList(Arrays.asList(
            VIEW_REQUEST_COUNT, VIEW_REQUEST_FAILED_COUNT, VIEW_REQUEST_CANCELED_COUNT, VIEW_PREROLL_REQUEST_COUNT,
            VIEW_PREROLL_AD_ID, VIEW_PREROLL_CREATIVE_ID, VIEW_PREROLL_REQUESTED, VIEW_PREROLL_PLAYED,
            VIEW_PREROLL_REQUEST_TIME, VIEW_STARTUP_PREROLL_REQUEST_TIME, VIEW_PREROLL_LOAD_TIME,
            VIEW_STARTUP_PREROLL_LOAD_TIME, VIEW_END_TIME, VIEW_ID, VIEW_MAX_DOWNSCALE_PERCENTAGE,
            VIEW_MAX_SEEK_TIME, VIEW_MAX_UPSCALE_PERCENTAGE, VIEW_MIDROLL_TIME_TO_FIRST_AD, VIEW_PERCENTAGE_VIEWED,
            VIEW_REBUFFER_COUNT, VIEW_REBUFFER_DURATION, VIEW_REBUFFER_FREQUENCY, VIEW_REBUFFER_PERCENTAGE,
            VIEW_SEEK_COUNT, VIEW_SEEK_DURATION, VIEW_SEQUENCE_NUMBER, VIEW_START, VIEW_TIME_TO_FIRST_FRAME,
            VIEW_TIME_TO_PREROLL, VIEW_CONTENT_PLAYBACK_TIME, VIEW_TOTAL_CONTENT_PLAYBACK_TIME,
            VIEW_TOTAL_DOWNSCALING, VIEW_TOTAL_UPSCALING, VIEW_WAITING_REBUFFER_COUNT, VIEW_WAITING_REBUFFER_DURATION,
            VIEW_WATCH_TIME, VIEWER_TIME, VIEW_MIN_REQUEST_THROUGHPUT, VIEW_AVERAGE_REQUEST_THROUGHPUT,
            VIEW_MAX_REQUEST_LATENCY, VIEW_AVERAGE_REQUEST_LATENCY, VIEW_DEVICE_ORIENTATION, VIEW_DROPPED_FRAMES,
            VIEW_PROGRAM_CHANGED, VIEW_MAX_PLAYHEAD_POSITION, VIEW_DRM_TYPE
    ));
    // Default constructor
    public ViewDataEntity() {
    }

    public void sync() {
    }

    // Sets the count of view requests. If the count is not null, it stores it using the appropriate constant key.
    public void setViewRequestCount(Long viewRequestCount) throws JSONException {
        if (viewRequestCount != null) {
            this.put(VIEW_REQUEST_COUNT, viewRequestCount);
        }
    }

    // Gets the count of view requests. Retrieves the stored value and returns it as a Long.
    // If no value is found, returns null.
    public Long getViewRequestCount() {
        String requestCount = this.get(VIEW_REQUEST_COUNT);
        return requestCount == null ? null : Long.parseLong(requestCount);
    }

    // Sets the count of failed view requests. If the count is not null, it stores it using the appropriate constant key.

    public void setViewRequestFailedCount(Long viewRequestFailedCount) throws JSONException {
        if (viewRequestFailedCount != null) {
            this.put(VIEW_REQUEST_FAILED_COUNT, viewRequestFailedCount);
        }
    }

    // Gets the count of failed view requests. Retrieves the stored value and returns it as a Long.
    // If no value is found, returns null.
    public Long getViewRequestFailedCount() {
        String failedCount = this.get(VIEW_REQUEST_FAILED_COUNT);
        return failedCount == null ? null : Long.parseLong(failedCount);
    }

    // Sets the count of canceled view requests. If the count is not null, it stores it using the appropriate constant key.
    public void setViewRequestCanceledCount(Long viewRequestCanceledCount) throws JSONException {
        if (viewRequestCanceledCount != null) {
            this.put(VIEW_REQUEST_CANCELED_COUNT, viewRequestCanceledCount);
        }
    }

    // Gets the count of canceled view requests. Retrieves the stored value and returns it as a Long.
    // If no value is found, returns null.
    public Long getViewRequestCanceledCount() {
        String count = this.get(VIEW_REQUEST_CANCELED_COUNT);
        return count == null ? null : Long.parseLong(count);
    }

    // Sets the count of preroll requests. If the preroll request count is not null, it stores it using the appropriate constant key.
    public void setViewPrerollRequestCount(Long viewPrerollRequestCount) throws JSONException {
        if (viewPrerollRequestCount != null) {
            this.put(VIEW_PREROLL_REQUEST_COUNT, viewPrerollRequestCount);
        }
    }

    // Gets the count of preroll requests. Retrieves the stored value and returns it as a Long.
// If no value is found, returns null.
    public Long getViewPrerollRequestCount() {
        String requestCount = this.get(VIEW_PREROLL_REQUEST_COUNT);
        return requestCount == null ? null : Long.parseLong(requestCount);
    }

    // Sets the end time of the view. If the view end time is not null, it stores it using the appropriate constant key.

    public void setViewEnd(Long viewEnd) throws JSONException {
        if (viewEnd != null) {
            this.put(VIEW_END_TIME, viewEnd);
        }
    }

    // Gets the end time of the view. Retrieves the stored value and returns it as a Long.
// If no value is found, it returns 0L (representing a default value).
    public Long getViewEnd() {
        return (this.get(VIEW_END_TIME)) == null ? null : 0L;
    }

    // Sets the view ID. If the view ID is not null, it stores it using the appropriate constant key.

    public void setViewId(String viewId) throws JSONException {
        if (viewId != null) {
            this.put(VIEW_ID, viewId);
        }
    }

    // Gets the view ID. Retrieves the stored view ID value as a String.

    public String getViewId() {
        return this.get(VIEW_ID);
    }

    // Sets the maximum downscale percentage for the view. If the value is not null, it stores it using the appropriate constant key.
    public void setViewMaxDownscalePercentage(Double viewMaxDownscalePercentage) throws JSONException {
        if (viewMaxDownscalePercentage != null) {
            this.put(VIEW_MAX_DOWNSCALE_PERCENTAGE, viewMaxDownscalePercentage);
        }
    }

    // Gets the maximum downscale percentage for the view. Retrieves the stored value and returns it as a Double.
// If no value is found, returns null.
    public Double getViewMaxDownscalePercentage() {
        String percentage = this.get(VIEW_MAX_DOWNSCALE_PERCENTAGE);
        return percentage == null ? null : Double.parseDouble(percentage);
    }

    // Sets the maximum seek time for the view. If the value is not null, it stores it using the appropriate constant key.

    public void setViewMaxSeekTime(Long viewMaxSeekTime) throws JSONException {
        if (viewMaxSeekTime != null) {
            this.put(VIEW_MAX_SEEK_TIME, viewMaxSeekTime);
        }
    }

    // Gets the maximum seek time for the view. Retrieves the stored value and returns it as a Long.
// If no value is found, returns null.
    public Long getViewMaxSeekTime() {
        String seekTime = this.get(VIEW_MAX_SEEK_TIME);
        return seekTime == null ? null : Long.parseLong(seekTime);
    }

    // Sets the maximum upscale percentage for the view. If the value is not null, it stores it using the appropriate constant key.

    public void setViewMaxUpscalePercentage(Double upscalValue) throws JSONException {
        if (upscalValue != null) {
            this.put(VIEW_MAX_UPSCALE_PERCENTAGE, upscalValue);
        }
    }

    // Gets the maximum upscale percentage for the view. Retrieves the stored value and returns it as a Double.
// If no value is found, returns null.
    public Double getViewMaxUpscalePercentage() {
        String upscale = this.get(VIEW_MAX_UPSCALE_PERCENTAGE);
        return upscale == null ? null : Double.parseDouble(upscale);
    }

    // Sets the midroll time to the first ad for the view. If the value is not null, it stores it using the appropriate constant key.

    public void setViewMidrollTimeToFirstAd(Long viewMidrollTimeToFirstAd) throws JSONException {
        if (viewMidrollTimeToFirstAd != null) {
            this.put(VIEW_MIDROLL_TIME_TO_FIRST_AD, viewMidrollTimeToFirstAd);
        }
    }

    // Gets the midroll time to the first ad for the view. Retrieves the stored value and returns it as a Long.
// If no value is found, returns null.
    public Long getViewMidrollTimeToFirstAd() {
        String firstAdd = this.get(VIEW_MIDROLL_TIME_TO_FIRST_AD);
        return firstAdd == null ? null : Long.parseLong(firstAdd);
    }

    // Sets the percentage of the view that has been watched. If the value is not null, it stores it using the appropriate constant key.

    public void setViewPercentageViewed(Integer viewPercentageViewed) throws JSONException {
        if (viewPercentageViewed != null) {
            this.put(VIEW_PERCENTAGE_VIEWED, viewPercentageViewed);
        }
    }

    // Gets the percentage of the view that has been watched. Retrieves the stored value and returns it as an Integer.
    // If no value is found, returns null.
    public Integer getViewPercentageViewed() {
        String viewed = this.get(VIEW_PERCENTAGE_VIEWED);
        return viewed == null ? null : Integer.parseInt(viewed);
    }

    // Sets the rebuffer count for the view. If the value is not null, it stores it using the appropriate constant key.

    public void setViewRebufferCount(Integer viewRebufferCount) throws JSONException {
        if (viewRebufferCount != null) {
            this.put(VIEW_REBUFFER_COUNT, viewRebufferCount);
        }
    }

    // Gets the rebuffer count for the view. Retrieves the stored value and returns it as an Integer.
    // If no value is found, returns null.
    public Integer getViewRebufferCount() {
        String rebuffedCount = this.get(VIEW_REBUFFER_COUNT);
        return rebuffedCount == null ? null : Integer.parseInt(rebuffedCount);
    }

    // Sets the rebuffer duration for the view. If the value is not null, it stores it using the appropriate constant key.

    public void setViewRebufferDuration(Long viewRebufferDuration) throws JSONException {
        if (viewRebufferDuration != null) {
            this.put(VIEW_REBUFFER_DURATION, viewRebufferDuration);
        }
    }


    // Gets the rebuffer duration for the view. Retrieves the stored value and returns it as a Long.
    // If no value is found, returns null.
    public Long getViewRebufferDuration() {
        String rebuffedDuration = this.get(VIEW_REBUFFER_DURATION);
        return rebuffedDuration == null ? null : Long.parseLong(rebuffedDuration);
    }

    // Sets the rebuffer frequency for the view. If the value is not null, it stores it using the appropriate constant key.

    public void setViewRebufferFrequency(Double viewRebufferFrequency) throws JSONException {
        if (viewRebufferFrequency != null) {
            this.put(VIEW_REBUFFER_FREQUENCY, viewRebufferFrequency);
        }
    }

    // Gets the rebuffer frequency for the view. Retrieves the stored value and returns it as a Double.
    // If no value is found, returns null.
    public Double getViewRebufferFrequency() {
        String frequency = this.get(VIEW_REBUFFER_FREQUENCY);
        return frequency == null ? null : Double.parseDouble(frequency);
    }

    // Sets the rebuffer percentage for the view. If the value is not null, it stores it using the appropriate constant key.
    public void setViewRebufferPercentage(Double viewRebufferPercentage) throws JSONException {
        if (viewRebufferPercentage != null) {
            this.put(VIEW_REBUFFER_PERCENTAGE, viewRebufferPercentage);
        }
    }

    // Gets the rebuffer percentage for the view. Retrieves the stored value and returns it as a Double.
    // If no value is found, returns null.
    public Double getViewRebufferPercentage() {
        String percentage = this.get(VIEW_REBUFFER_PERCENTAGE);
        return percentage == null ? null : Double.parseDouble(percentage);
    }

    // Sets the seek count for the view. If the value is not null, it stores it using the appropriate constant key.
    public void setViewSeekCount(Integer viewSeekCount) throws JSONException {
        if (viewSeekCount != null) {
            this.put(VIEW_SEEK_COUNT, viewSeekCount);
        }
    }

    // Gets the seek count for the view. Retrieves the stored value and returns it as an Integer.
    // If no value is found, returns null.
    public Integer getViewSeekCount() {
        String seekCount = this.get(VIEW_SEEK_COUNT);
        return seekCount == null ? null : Integer.parseInt(seekCount);
    }

    // Sets the seek duration for the view. If the value is not null, it stores it using the appropriate constant key.
    public void setViewSeekDuration(Long viewSeekDuration) throws JSONException {
        if (viewSeekDuration != null) {
            this.put(VIEW_SEEK_DURATION, viewSeekDuration);
        }
    }

    // Gets the seek duration for the view. Retrieves the stored value and returns it as a Long.
    // If no value is found, returns null.
    public Long getViewSeekDuration() {
        String seekDuration = this.get(VIEW_SEEK_DURATION);
        return seekDuration == null ? null : Long.parseLong(seekDuration);
    }

    // Sets the sequence number for the view. If the value is not null, it stores it using the appropriate constant key.
    public void setViewSequenceNumber(Integer viewSequenceNumber) throws JSONException {
        if (viewSequenceNumber != null) {
            this.put(VIEW_SEQUENCE_NUMBER, viewSequenceNumber);
        }
    }

    // Gets the sequence number for the view. Retrieves the stored value and returns it as an Integer.
    // If no value is found, returns null.
    public Integer getViewSequenceNumber() {
        String number = this.get(VIEW_SEQUENCE_NUMBER);
        return number == null ? null : Integer.parseInt(number);
    }

    // Sets the start time for the view. If the value is not null, it stores it using the appropriate constant key.
    public void setViewStart(Long viewStart) throws JSONException {
        if (viewStart != null) {
            this.put(VIEW_START, viewStart);
        }
    }

    // Gets the start time for the view. Retrieves the stored value and returns it as a Long.
    // If no value is found, returns null.
    public Long getViewStart() {
        String viewStart = this.get(VIEW_START);
        return viewStart == null ? null : Long.parseLong(viewStart);
    }

    // Sets the time to the first frame for the view. If the value is not null, it stores it using the appropriate constant key.
    public void setViewTimeToFirstFrame(Long viewTimeToFirstFrame) throws JSONException {
        if (viewTimeToFirstFrame != null) {
            this.put(VIEW_TIME_TO_FIRST_FRAME, viewTimeToFirstFrame);
        }
    }


    // Retrieves the time taken to load the first frame for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewTimeToFirstFrame() {
        String firstFrame = this.get(VIEW_TIME_TO_FIRST_FRAME);
        return firstFrame == null ? null : Long.parseLong(firstFrame);
    }

    // Sets the time to preroll for the view. If the value is not null, stores it using the appropriate constant key.
    public void setViewTimeToPreroll(Long viewTimeToPreroll) throws JSONException {
        if (viewTimeToPreroll != null) {
            this.put(VIEW_TIME_TO_PREROLL, viewTimeToPreroll);
        }
    }

    // Retrieves the time to preroll for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewTimeToPreroll() {
        String preroll = this.get(VIEW_TIME_TO_PREROLL);
        return preroll == null ? null : Long.parseLong(preroll);
    }

    // Sets the content playback time for the view. If the value is not null, stores it using the appropriate constant key.
    public void setViewContentPlaybackTime(Long viewContentPlaybackTime) throws JSONException {
        if (viewContentPlaybackTime != null) {
            this.put(VIEW_CONTENT_PLAYBACK_TIME, viewContentPlaybackTime);
        }
    }

    // Retrieves the content playback time for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewContentPlaybackTime() {
        String playbackTime = this.get(VIEW_CONTENT_PLAYBACK_TIME);
        return playbackTime == null ? null : Long.parseLong(playbackTime);
    }

    // Sets the total content playback time for the view. If the value is not null, stores it using the appropriate constant key.
    public void setViewTotalContentPlaybackTime(Long viewTotalContentPlaybackTime) throws JSONException {
        if (viewTotalContentPlaybackTime != null) {
            this.put(VIEW_TOTAL_CONTENT_PLAYBACK_TIME, viewTotalContentPlaybackTime);
        }
    }

    // Retrieves the total content playback time for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewTotalContentPlaybackTime() {
        String playbackTime = this.get(VIEW_TOTAL_CONTENT_PLAYBACK_TIME);
        return playbackTime == null ? null : Long.parseLong(playbackTime);
    }

    // Sets the total downscaling percentage for the view. If the value is not null, stores it using the appropriate constant key.
    public void setViewTotalDownscaling(Double viewTotalDownscaling) throws JSONException {
        if (viewTotalDownscaling != null) {
            this.put(VIEW_TOTAL_DOWNSCALING, viewTotalDownscaling);
        }
    }

    // Retrieves the total downscaling percentage for the view. Converts the stored value to a Double.
    // If no value is found, returns null.
    public Double getViewTotalDownscaling() {
        String downscaling = this.get(VIEW_TOTAL_DOWNSCALING);
        return downscaling == null ? null : Double.parseDouble(downscaling);
    }

    // Sets the total upscaling value for the view. If the value is not null, stores it using the constant key.

    public void setViewTotalUpscaling(Double viewTotalUpscaling) throws JSONException {
        if (viewTotalUpscaling != null) {
            this.put(VIEW_TOTAL_UPSCALING, viewTotalUpscaling);
        }
    }

    // Retrieves the total upscaling value for the view. Converts the stored value to a Double.
    // If no value is found, returns null.
    public Double getViewTotalUpscaling() {
        String viewTotalUpScaling = this.get(VIEW_TOTAL_UPSCALING);
        return viewTotalUpScaling == null ? null : Double.parseDouble(viewTotalUpScaling);
    }

    // Sets the waiting rebuffer count for the view. If the value is not null, stores it using the constant key.
    public void setViewWaitingRebufferCount(Integer viewWaitingRebufferCount) throws JSONException {
        if (viewWaitingRebufferCount != null) {
            this.put(VIEW_WAITING_REBUFFER_COUNT, viewWaitingRebufferCount);
        }
    }

    // Retrieves the waiting rebuffer count for the view. Converts the stored value to an Integer.
    // If no value is found, returns null.
    public Integer getViewWaitingRebufferCount() {
        String rebuffedCount = this.get(VIEW_WAITING_REBUFFER_COUNT);
        return rebuffedCount == null ? null : Integer.parseInt(rebuffedCount);
    }

    // Sets the waiting rebuffer duration for the view. If the value is not null, stores it using the constant key.
    public void setViewWaitingRebufferDuration(Long viewWaitingRebufferDuration) throws JSONException {
        if (viewWaitingRebufferDuration != null) {
            this.put(VIEW_WAITING_REBUFFER_DURATION, viewWaitingRebufferDuration);
        }
    }

    // Retrieves the waiting rebuffer duration for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewWaitingRebufferDuration() {
        String duration = this.get(VIEW_WAITING_REBUFFER_DURATION);
        return duration == null ? null : Long.parseLong(duration);
    }

    // Sets the total watch time for the view. If the value is not null, stores it using the constant key.
    public void setViewWatchTime(Long viewWatchTime) throws JSONException {
        if (viewWatchTime != null) {
            this.put(VIEW_WATCH_TIME, viewWatchTime);
        }
    }

    // Retrieves the total watch time for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewWatchTime() {
        String watchTime = this.get(VIEW_WATCH_TIME);
        return watchTime == null ? null : Long.parseLong(watchTime);
    }

    // Sets the viewer time for the view. If the value is not null, stores it using the constant key.
    public void setViewerTime(Long viewerTime) throws JSONException {
        if (viewerTime != null) {
            this.put(VIEWER_TIME, viewerTime);
        }
    }

    // Retrieves the viewer time for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewerTime() {
        String viewerTime = this.get(VIEWER_TIME);
        return viewerTime == null ? null : Long.parseLong(viewerTime);
    }

    // Sets the minimum request throughput for the view. If the value is not null, stores it using the constant key.
    public void setViewMinRequestThroughput(Long viewMinRequestThroughput) throws JSONException {
        if (viewMinRequestThroughput != null) {
            this.put(VIEW_MIN_REQUEST_THROUGHPUT, viewMinRequestThroughput);
        }
    }

    // Retrieves the minimum request throughput for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewMinRequestThroughput() {
        String throughput = this.get(VIEW_MIN_REQUEST_THROUGHPUT);
        return throughput == null ? null : Long.parseLong(throughput);
    }

    // Sets the average request throughput for the view. If the value is not null, stores it using the constant key.
    public void setViewAverageRequestThroughput(Long viewAverageRequestThroughput) throws JSONException {
        if (viewAverageRequestThroughput != null) {
            this.put(VIEW_AVERAGE_REQUEST_THROUGHPUT, viewAverageRequestThroughput);
        }
    }

    // Retrieves the average request throughput for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewAverageRequestThroughput() {
        String throughput = this.get(VIEW_AVERAGE_REQUEST_THROUGHPUT);
        return throughput == null ? null : Long.parseLong(throughput);
    }

    // Sets the maximum request latency for the view. If the value is not null, stores it using the constant key.
    public void setViewMaxRequestLatency(Double viewMaxRequestLatency) throws JSONException {
        if (viewMaxRequestLatency != null) {
            this.put(VIEW_MAX_REQUEST_LATENCY, viewMaxRequestLatency);
        }
    }

    // Retrieves the maximum request latency for the view. Converts the stored value to a Double.
    // If no value is found, returns null.
    public Double getViewMaxRequestLatency() {
        String latency = this.get(VIEW_MAX_REQUEST_LATENCY);
        return latency == null ? null : Double.parseDouble(latency);
    }

    // Sets the average request latency for the view. If the value is not null, stores it using the constant key.
    public void setViewAverageRequestLatency(Double viewAverageRequestLatency) throws JSONException {
        if (viewAverageRequestLatency != null) {
            this.put(VIEW_AVERAGE_REQUEST_LATENCY, viewAverageRequestLatency);
        }
    }

    // Retrieves the average request latency for the view. Converts the stored value to a Double.
    // If no value is found, returns null.
    public Double getViewAverageRequestLatency() {
        String latency = this.get(VIEW_AVERAGE_REQUEST_LATENCY);
        return latency == null ? null : Double.parseDouble(latency);
    }

    // Sets the preroll creative ID for the view. If the value is not null, stores it using the constant key.
    public void setViewPrerollCreativeId(String viewPrerollCreativeId) throws JSONException {
        if (viewPrerollCreativeId != null) {
            this.put(VIEW_PREROLL_CREATIVE_ID, viewPrerollCreativeId);
        }
    }

    // Retrieves the preroll creative ID for the view. Returns the stored value.
    public String getViewPrerollCreativeId() {
        return this.get(VIEW_PREROLL_CREATIVE_ID);
    }

    // Sets whether the preroll ad was requested for the view. If the value is not null, stores it using the constant key.
    public void setViewPrerollRequested(Boolean viewPrerollRequested) throws JSONException {
        if (viewPrerollRequested != null) {
            this.put(VIEW_PREROLL_REQUESTED, viewPrerollRequested);
        }
    }

    // Retrieves whether the preroll ad was requested for the view. Converts the stored value to a Boolean.
    // If no value is found, returns null.
    public Boolean getViewPrerollRequested() {
        String requested = this.get(VIEW_PREROLL_REQUESTED);
        return requested == null ? null : Boolean.parseBoolean(requested);
    }

    // Sets whether the preroll ad was played for the view. If the value is not null, stores it using the constant key.
    public void setViewPrerollPlayed(Boolean viewPrerollPlayed) throws JSONException {
        if (viewPrerollPlayed != null) {
            this.put(VIEW_PREROLL_PLAYED, viewPrerollPlayed);
        }
    }

    // Retrieves whether the preroll ad was played for the view. Converts the stored value to a Boolean.
    // If no value is found, returns null.
    public Boolean getViewPrerollPlayed() {
        String player = this.get(VIEW_PREROLL_PLAYED);
        return player == null ? null : Boolean.parseBoolean(player);
    }

    // Sets the time when the preroll ad was requested for the view. If the value is not null, stores it using the constant key.
    public void setViewPrerollRequestTime(Long viewPrerollRequestTime) throws JSONException {
        if (viewPrerollRequestTime != null) {
            this.put(VIEW_PREROLL_REQUEST_TIME, viewPrerollRequestTime);
        }
    }

    // Retrieves the time when the preroll ad was requested for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewPrerollRequestTime() {
        String requestTime = this.get(VIEW_PREROLL_REQUEST_TIME);
        return requestTime == null ? null : Long.parseLong(requestTime);
    }

    // Sets the time when the startup preroll ad was requested for the view. If the value is not null, stores it using the constant key.
    public void setViewStartupPrerollRequestTime(Long viewStartupPrerollRequestTime) throws JSONException {
        if (viewStartupPrerollRequestTime != null) {
            this.put(VIEW_STARTUP_PREROLL_REQUEST_TIME, viewStartupPrerollRequestTime);
        }
    }

    // Retrieves the time when the startup preroll ad was requested for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewStartupPrerollRequestTime() {
        String requestTime = this.get(VIEW_STARTUP_PREROLL_REQUEST_TIME);
        return requestTime == null ? null : Long.parseLong(requestTime);
    }

    // Sets the time taken to load the preroll ad for the view. If the value is not null, stores it using the constant key.
    public void setViewPrerollLoadTime(Long viewPrerollLoadTime) throws JSONException {
        if (viewPrerollLoadTime != null) {
            this.put(VIEW_PREROLL_LOAD_TIME, viewPrerollLoadTime);
        }
    }

    // Retrieves the time taken to load the preroll ad for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewPrerollLoadTime() {
        String loadTime = this.get(VIEW_PREROLL_LOAD_TIME);
        return loadTime == null ? null : Long.parseLong(loadTime);
    }

    // Sets the time taken to load the startup preroll ad for the view. If the value is not null, stores it using the constant key.
    public void setViewStartupPrerollLoadTime(Long viewStartupPrerollLoadTime) throws JSONException {
        if (viewStartupPrerollLoadTime != null) {
            this.put(VIEW_STARTUP_PREROLL_LOAD_TIME, viewStartupPrerollLoadTime);
        }
    }

    // Retrieves the time taken to load the startup preroll ad for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewStartupPrerollLoadTime() {
        String loadTime = this.get(VIEW_STARTUP_PREROLL_LOAD_TIME);
        return loadTime == null ? null : Long.parseLong(loadTime);
    }

    // Sets whether the program for the view has changed. Stores the value using the constant key.
    public void setViewProgramChanged(Boolean viewProgramChanged) throws JSONException {
        this.put(VIEW_PROGRAM_CHANGED, viewProgramChanged);
    }

    // Retrieves whether the program for the view has changed. Converts the stored value to a Boolean.
    // If no value is found, returns null.
    public Boolean getViewProgramChanged() {
        String changed = this.get(VIEW_PROGRAM_CHANGED);
        return changed != null && Boolean.parseBoolean(changed);
    }


    // Sets the viewer's orientation data for the view by calling setViewOrientationData method.
    public void setViewerOrientationData(ViewDeviceOrientationDataEntity viewerOrientationData) throws JSONException {
        this.setViewOrientationData(viewerOrientationData);
    }

    // Sets the orientation data for the view. If the orientation data is not null, stores it using the constant key.
    public void setViewOrientationData(ViewDeviceOrientationDataEntity viewOrientationData) throws JSONException {
        if (viewOrientationData != null) {
            this.put(VIEW_DEVICE_ORIENTATION, viewOrientationData.toJsonString());
        }
    }

    // Retrieves the device orientation data for the view. If the data is stored, it attempts to convert it to a ViewDeviceOrientationData object.
    // Returns null if no data is found or if there is an issue with parsing the JSON.
    public ViewDeviceOrientationDataEntity getViewOrientationData() throws JSONException {
        String orientationData = this.get(VIEW_DEVICE_ORIENTATION);
        if (orientationData == null) {
            return null;
        } else {
            try {
                return new ViewDeviceOrientationDataEntity(new JSONObject(orientationData));
            } catch (JSONException jsonException) {
                return null;
            }
        }
    }

    // Sets the number of dropped frames for the view. The value is stored only if it's not null.
    public void setViewDroppedFrames(Long viewDroppedFrames) throws JSONException {
        if (viewDroppedFrames != null) {
            this.put(VIEW_DROPPED_FRAMES, viewDroppedFrames);
        }
    }

    // Retrieves the number of dropped frames for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewDroppedFrames() {
        String droppedFrames = this.get(VIEW_DROPPED_FRAMES);
        return droppedFrames == null ? null : Long.parseLong(droppedFrames);
    }

    // Retrieves the maximum playhead position for the view. Converts the stored value to a Long.
    // If no value is found, returns null.
    public Long getViewMaxPlayheadPosition() {
        String playheadPosition = this.get(VIEW_MAX_PLAYHEAD_POSITION);
        return playheadPosition == null ? null : Long.parseLong(playheadPosition);
    }

    // Sets the maximum playhead position for the view. The value is stored only if it's not null.
    public void setViewMaxPlayheadPosition(Long viewMaxPlayheadPosition) throws JSONException {
        if (viewMaxPlayheadPosition != null) {
            this.put(VIEW_MAX_PLAYHEAD_POSITION, viewMaxPlayheadPosition);
        }
    }

    // Retrieves the DRM type for the view. Returns the stored value.
    public String getViewDrmType() {
        return this.get(VIEW_DRM_TYPE);
    }

    // Sets the DRM type for the view. The value is stored only if it's not null.
    public void setDrmType(String drmType) throws JSONException {
        if (drmType != null) {
            this.put(VIEW_DRM_TYPE, drmType);
        }
    }

    // Static initialization block to populate the keys list
   /* static {
        List<String> keys = new ArrayList();
        keys.add(VIEW_REQUEST_COUNT);
        keys.add(VIEW_REQUEST_FAILED_COUNT);
        keys.add(VIEW_REQUEST_CANCELED_COUNT);
        keys.add(VIEW_PREROLL_REQUEST_COUNT);
        keys.add(VIEW_PREROLL_AD_ID);
        keys.add(VIEW_PREROLL_CREATIVE_ID);
        keys.add(VIEW_PREROLL_REQUESTED);
        keys.add(VIEW_PREROLL_PLAYED);
        keys.add(VIEW_PREROLL_REQUEST_TIME);
        keys.add(VIEW_STARTUP_PREROLL_REQUEST_TIME);
        keys.add(VIEW_PREROLL_LOAD_TIME);
        keys.add(VIEW_STARTUP_PREROLL_LOAD_TIME);
        keys.add(VIEW_END_TIME);
        keys.add(VIEW_ID);
        keys.add(VIEW_MAX_DOWNSCALE_PERCENTAGE);
        keys.add(VIEW_MAX_SEEK_TIME);
        keys.add(VIEW_MAX_UPSCALE_PERCENTAGE);
        keys.add(VIEW_MIDROLL_TIME_TO_FIRST_AD);
        keys.add(VIEW_PERCENTAGE_VIEWED);
        keys.add(VIEW_REBUFFER_COUNT);
        keys.add(VIEW_REBUFFER_DURATION);
        keys.add(VIEW_REBUFFER_FREQUENCY);
        keys.add(VIEW_REBUFFER_PERCENTAGE);
        keys.add(VIEW_SEEK_COUNT);
        keys.add(VIEW_SEEK_DURATION);
        keys.add(VIEW_SEQUENCE_NUMBER);
        keys.add(VIEW_START);
        keys.add(VIEW_TIME_TO_FIRST_FRAME);
        keys.add(VIEW_TIME_TO_PREROLL);
        keys.add(VIEW_CONTENT_PLAYBACK_TIME);
        keys.add(VIEW_TOTAL_CONTENT_PLAYBACK_TIME);
        keys.add(VIEW_TOTAL_DOWNSCALING);
        keys.add(VIEW_TOTAL_UPSCALING);
        keys.add(VIEW_WAITING_REBUFFER_COUNT);
        keys.add(VIEW_WAITING_REBUFFER_DURATION);
        keys.add(VIEW_WATCH_TIME);
        keys.add(VIEWER_TIME);
        keys.add(VIEW_MIN_REQUEST_THROUGHPUT);
        keys.add(VIEW_AVERAGE_REQUEST_THROUGHPUT);
        keys.add(VIEW_MAX_REQUEST_LATENCY);
        keys.add(VIEW_AVERAGE_REQUEST_LATENCY);
        keys.add(VIEW_DEVICE_ORIENTATION);
        keys.add(VIEW_DROPPED_FRAMES);
        keys.add(VIEW_PROGRAM_CHANGED);
        keys.add(VIEW_MAX_PLAYHEAD_POSITION);
        keys.add(VIEW_DRM_TYPE);
        keysViewData = Collections.unmodifiableList(keys);
    }*/
}

