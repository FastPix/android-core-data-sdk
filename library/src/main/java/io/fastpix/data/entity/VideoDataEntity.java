package io.fastpix.data.entity;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class holds various constants related to video data and provides methods to set and get
 * specific video properties such as the poster URL, bitrate, frame rate, codec, etc.
 * It extends the BaseQueryData class to inherit the functionality for handling JSON data.
 */
public class VideoDataEntity extends QueryDataEntity {

    // Constants for video properties
    // These constants represent the keys used in the data object for each property.

    // URL for the video poster image
    public static final String VIDEO_POSTER_URL = "vdpour";

    // Bitrate of the video source
    public static final String VIDEO_SOURCE_BITRATE = "vdsobi";

    // Advertised frame rate of the video source
    public static final String VIDEO_SOURCE_ADVERTISED_FRAME_RATE = "vdsoatfrre";

    // Domain where the video source is hosted
    public static final String VIDEO_SOURCE_DOMAIN = "vdsodn";

    // Duration of the video source in seconds
    public static final String VIDEO_SOURCE_DURATION = "vdsodu";

    // Height of the video source (in pixels)
    public static final String VIDEO_SOURCE_HEIGHT = "vdsoht";

    // Hostname of the server hosting the video source
    public static final String VIDEO_SOURCE_HOSTNAME = "vdsohn";

    // Flag indicating if the video source is live
    public static final String VIDEO_SOURCE_IS_LIVE = "vdsoisli";

    // MIME type of the video source (e.g., video/mp4)
    public static final String VIDEO_SOURCE_MIME_TYPE = "vdsomity";

    // URL to the video source
    public static final String VIDEO_SOURCE_URL = "vdsour";

    // Width of the video source (in pixels)
    public static final String VIDEO_SOURCE_WIDTH = "vdsowt";

    // Holdback time for the video (buffering time before playback starts)
    public static final String VIDEO_HOLDBACK = "vdhb";

    // Holdback time for a specific part of the video
    public static final String VIDEO_PART_HOLDBACK = "vdpthb";

    // Target duration for a specific part of the video
    public static final String VIDEO_PART_TARGET_DURATION = "vdpttgdu";

    // Target duration of the entire video
    public static final String VIDEO_TARGET_DURATION = "vdtgdu";

    // Codec used for the video source (e.g., H.264, VP9)
    public static final String VIDEO_SOURCE_CODEC = "vdsocc";

    // Frames per second (FPS) of the video source
    public static final String VIDEO_SOURCE_FPS = "vdsofs";

    // List of all keys in the VideoData class (useful for iteration or validation)
//    public static final List<String> keysVData;

    public static final List<String> keysVData = Collections.unmodifiableList(Arrays.asList(
            VIDEO_POSTER_URL, VIDEO_SOURCE_BITRATE, VIDEO_SOURCE_ADVERTISED_FRAME_RATE, VIDEO_SOURCE_DOMAIN,
            VIDEO_SOURCE_DURATION, VIDEO_SOURCE_HEIGHT, VIDEO_SOURCE_HOSTNAME, VIDEO_SOURCE_IS_LIVE,
            VIDEO_SOURCE_MIME_TYPE, VIDEO_SOURCE_URL, VIDEO_SOURCE_WIDTH, VIDEO_HOLDBACK,
            VIDEO_PART_HOLDBACK, VIDEO_PART_TARGET_DURATION, VIDEO_TARGET_DURATION, VIDEO_SOURCE_CODEC,
            VIDEO_SOURCE_FPS
    ));

    // Static initialization block to populate the keys list
   /* static {
        List<String> keys = new ArrayList<>();
        keys.add(VIDEO_POSTER_URL);
        keys.add(VIDEO_SOURCE_BITRATE);
        keys.add(VIDEO_SOURCE_ADVERTISED_FRAME_RATE);
        keys.add(VIDEO_SOURCE_DOMAIN);
        keys.add(VIDEO_SOURCE_DURATION);
        keys.add(VIDEO_SOURCE_HEIGHT);
        keys.add(VIDEO_SOURCE_HOSTNAME);
        keys.add(VIDEO_SOURCE_IS_LIVE);
        keys.add(VIDEO_SOURCE_MIME_TYPE);
        keys.add(VIDEO_SOURCE_URL);
        keys.add(VIDEO_SOURCE_WIDTH);
        keys.add(VIDEO_HOLDBACK);
        keys.add(VIDEO_PART_HOLDBACK);
        keys.add(VIDEO_PART_TARGET_DURATION);
        keys.add(VIDEO_TARGET_DURATION);
        keys.add(VIDEO_SOURCE_CODEC);
        keys.add(VIDEO_SOURCE_FPS);

        keysVData = Collections.unmodifiableList(keys);


    }
*/
    // Default constructor
    public VideoDataEntity() {
    }

    // Synchronization method (can be used for data syncing logic)
    public void sync() {
    }

    /**
     * Sets the video source codec (e.g., H.264, VP9).
     * @param videoSourceCodec The codec used for the video source.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceCodec(String videoSourceCodec) throws JSONException {
        if (videoSourceCodec != null) {
            this.put(VIDEO_SOURCE_CODEC, videoSourceCodec);
        }
    }

    /**
     * Gets the video source codec.
     * @return The codec used for the video source.
     */
    public String getVideoSourceCodec() {
        return this.get(VIDEO_SOURCE_CODEC);
    }

    /**
     * Sets the video poster URL (the image displayed as the video thumbnail).
     * @param videoPosterUrl The URL to the video poster image.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoPosterUrl(String videoPosterUrl) throws JSONException {
        if (videoPosterUrl != null) {
            this.put(VIDEO_POSTER_URL, videoPosterUrl);
        }
    }

    /**
     * Gets the video poster URL.
     * @return The URL to the video poster image.
     */
    public String getVideoPosterUrl() {
        return this.get(VIDEO_POSTER_URL);
    }

    /**
     * Sets the advertised bitrate for the video source.
     * This is typically the maximum bitrate that the video is advertised to support.
     * @param videoSourceAdvertisedBitrate The advertised bitrate of the video source in kbps.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceAdvertisedBitrate(Integer videoSourceAdvertisedBitrate) throws JSONException {
        if (videoSourceAdvertisedBitrate != null) {
            this.put(VIDEO_SOURCE_BITRATE, videoSourceAdvertisedBitrate);
        }
    }

    /**
     * Gets the advertised bitrate for the video source.
     * @return The advertised bitrate in kbps, or null if not set.
     */
    public Integer getVideoSourceAdvertisedBitrate() {
        String bitrate = this.get(VIDEO_SOURCE_BITRATE);
        return bitrate == null ? null : Integer.parseInt(bitrate);
    }

    /**
     * Sets the advertised frame rate for the video source.
     * This is the frame rate the video is advertised to support, often in frames per second (fps).
     * @param videoSourceAdvertisedFramerate The advertised frame rate of the video source in fps.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceAdvertisedFramerate(Float videoSourceAdvertisedFramerate) throws JSONException {
        if (videoSourceAdvertisedFramerate != null) {
            this.put(VIDEO_SOURCE_ADVERTISED_FRAME_RATE, videoSourceAdvertisedFramerate.doubleValue());
        }
    }

    /**
     * Gets the advertised frame rate for the video source.
     * @return The advertised frame rate in frames per second, or null if not set.
     */
    public Float getVideoSourceAdvertisedFramerate() {
        String frameRate = this.get(VIDEO_SOURCE_ADVERTISED_FRAME_RATE);
        return frameRate == null ? null : Float.parseFloat(frameRate);
    }

    /**
     * Sets the domain of the video source.
     * The domain typically refers to the host or source of the video stream or file.
     * @param videoSourceDomain The domain of the video source (e.g., "example.com").
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceDomain(String videoSourceDomain) throws JSONException {
        if (videoSourceDomain != null) {
            this.put(VIDEO_SOURCE_DOMAIN, videoSourceDomain);
        }
    }

    /**
     * Gets the domain of the video source.
     * @return The domain of the video source, or null if not set.
     */
    public String getVideoSourceDomain() {
        return this.get(VIDEO_SOURCE_DOMAIN);
    }

    /**
     * Sets the duration of the video source.
     * The duration represents the total time length of the video in milliseconds.
     * @param videoSourceDuration The duration of the video source in milliseconds.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceDuration(Long videoSourceDuration) throws JSONException {
        if (videoSourceDuration != null) {
            this.put(VIDEO_SOURCE_DURATION, videoSourceDuration);
        }
    }

    /**
     * Gets the duration of the video source.
     * The duration is typically stored in milliseconds.
     * @return The duration of the video source in milliseconds, or null if not set.
     */
    public Long getVideoSourceDuration() {
        String sourceDuration = this.get(VIDEO_SOURCE_DURATION);
        return sourceDuration == null ? null : Long.parseLong(sourceDuration);
    }

    /**
     * Sets the height of the video source.
     * The height represents the vertical dimension (in pixels) of the video source.
     * @param videoSourceHeight The height of the video source (e.g., 1080 for Full HD).
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */

    public void setVideoSourceHeight(Integer videoSourceHeight) throws JSONException {
        if (videoSourceHeight != null) {
            this.put(VIDEO_SOURCE_HEIGHT, videoSourceHeight);
        }
    }

    /**
     * Gets the height of the video source.
     * @return The height of the video source in pixels, or null if not set.
     */
    public Integer getVideoSourceHeight() {
        String sourceHeight = this.get(VIDEO_SOURCE_HEIGHT);
        return sourceHeight == null ? null : Integer.parseInt(sourceHeight);
    }

    /**
     * Sets the frames per second (FPS) for the video source.
     * The FPS value indicates how many frames are displayed per second in the video stream.
     * @param videoSourceFPS The FPS value (e.g., 30 for 30 FPS).
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceFPS(Integer videoSourceFPS) throws JSONException {
        if (videoSourceFPS != null) {
            this.put(VIDEO_SOURCE_FPS, videoSourceFPS);
        }
    }

    /**
     * Gets the frames per second (FPS) for the video source.
     * The FPS value indicates how many frames are displayed per second in the video stream.
     * @return The FPS of the video source, or null if not set.
     */
    public Integer getVideoSourceFPS() {
        String videoSourceFPS = this.get(VIDEO_SOURCE_FPS);
        return videoSourceFPS == null ? null : Integer.parseInt(videoSourceFPS);
    }

    /**
     * Sets the hostname for the video source.
     * The hostname is typically the domain name or IP address where the video source is hosted.
     * @param videoSourceHostName The hostname of the video source.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceHostName(String videoSourceHostName) throws JSONException {
        if (videoSourceHostName != null) {
            this.put(VIDEO_SOURCE_HOSTNAME, videoSourceHostName);
        }
    }

    /**
     * Gets the hostname for the video source.
     * @return The hostname of the video source, or null if not set.
     */
    public String getVideoSourceHostName() {
        return this.get(VIDEO_SOURCE_HOSTNAME);
    }

    /**
     * Sets the live status of the video source.
     * The live status indicates whether the video source is a live stream or not.
     * @param videoSourceIsLive A string representing the live status of the video source (e.g., "true" or "false").
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceIsLive(String videoSourceIsLive) throws JSONException {
        if (videoSourceIsLive != null) {
            this.put(VIDEO_SOURCE_IS_LIVE, videoSourceIsLive);
        }
    }

    /**
     * Gets the live status of the video source.
     * This indicates whether the video source is a live stream or not.
     * @return The live status of the video source, or null if not set.
     */
    public String getVideoSourceIsLive() {
        return this.get(VIDEO_SOURCE_IS_LIVE);
    }

    /**
     * Sets the MIME type of the video source.
     * MIME type represents the type of media being served (e.g., "video/mp4", "application/x-mpegURL").
     * @param videoSourceMimeType The MIME type of the video source.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceMimeType(String videoSourceMimeType) throws JSONException {
        if (videoSourceMimeType != null) {
            this.put(VIDEO_SOURCE_MIME_TYPE, videoSourceMimeType);
        }
    }

    /**
     * Gets the MIME type of the video source.
     * @return The MIME type of the video source, or null if not set.
     */
    public String getVideoSourceMimeType() {
        return this.get(VIDEO_SOURCE_MIME_TYPE);
    }

    /**
     * Sets the URL of the video source.
     * The URL points to the location of the video source (could be a streaming URL or file URL).
     * @param videoSourceUrl The URL of the video source.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceUrl(String videoSourceUrl) throws JSONException {
        if (videoSourceUrl != null) {
            this.put(VIDEO_SOURCE_URL, videoSourceUrl);
        }
    }

    /**
     * Retrieves the URL of the video source.
     * The URL points to the location of the video file or stream.
     * @return The video source URL, or null if not set.
     */
    public String getVideoSourceUrl() {
        return this.get(VIDEO_SOURCE_URL);
    }

    /**
     * Sets the width of the video source.
     * This represents the resolution width of the video.
     * @param videoSourceWidth The width of the video source in pixels.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoSourceWidth(Integer videoSourceWidth) throws JSONException {
        if (videoSourceWidth != null) {
            this.put(VIDEO_SOURCE_WIDTH, videoSourceWidth);
        }
    }

    /**
     * Retrieves the width of the video source.
     * @return The width of the video source in pixels, or null if not set.
     */
    public Integer getVideoSourceWidth() {
        String sourceWidth = this.get(VIDEO_SOURCE_WIDTH);
        return sourceWidth == null ? null : Integer.parseInt(sourceWidth);
    }

    /**
     * Sets the video holdback value.
     * Video holdback defines a buffer duration before a live stream plays, helping with latency adjustments.
     * @param videoHoldback The holdback time in milliseconds.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoHoldback(Long videoHoldback) throws JSONException {
        if (videoHoldback != null) {
            this.put(VIDEO_HOLDBACK, videoHoldback);
        }
    }

    /**
     * Retrieves the video holdback duration.
     * Holdback is a buffer period before a live stream plays, helping to reduce latency issues.
     * @return The holdback duration in milliseconds, or null if not set.
     */
    public Long getVideoHoldback() {
        String videoHoldBack = this.get(VIDEO_HOLDBACK);
        return videoHoldBack == null ? null : Long.parseLong(videoHoldBack);
    }

    /**
     * Sets the part-wise video holdback duration.
     * This value defines the holdback for individual video parts in segmented streaming.
     * @param videoPartHoldback The holdback duration for video parts in milliseconds.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoPartHoldback(Long videoPartHoldback) throws JSONException {
        if (videoPartHoldback != null) {
            this.put(VIDEO_PART_HOLDBACK, videoPartHoldback);
        }
    }

    /**
     * Retrieves the part-wise video holdback duration.
     * @return The holdback duration for video parts in milliseconds, or null if not set.
     */
    public Long getVideoPartHoldback() {
        String videoPartHoldBAck = this.get(VIDEO_PART_HOLDBACK);
        return videoPartHoldBAck == null ? null : Long.parseLong(videoPartHoldBAck);
    }

    /**
     * Sets the target duration for video parts.
     * The target duration specifies the intended segment length for adaptive streaming.
     * @param videoPartTargetDuration The target duration for each video segment in milliseconds.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoPartTargetDuration(Long videoPartTargetDuration) throws JSONException {
        if (videoPartTargetDuration != null) {
            this.put(VIDEO_PART_TARGET_DURATION, videoPartTargetDuration);
        }
    }

    /**
     * Retrieves the target duration for a video segment.
     * This value represents the intended duration of a segment in adaptive streaming.
     * @return The target duration in milliseconds, or null if not set.
     */
    public Long getVideoPartTargetDuration() {
        String duration = this.get(VIDEO_PART_TARGET_DURATION);
        return duration == null ? null : Long.parseLong(duration);
    }

    /**
     * Sets the overall target duration for the video.
     * This value represents the recommended duration for a video chunk in HLS/DASH streaming.
     * @param videoTargetDuration The target duration in milliseconds.
     * @throws JSONException If there is an error while setting the JSON key-value pair.
     */
    public void setVideoTargetDuration(Long videoTargetDuration) throws JSONException {
        if (videoTargetDuration != null) {
            this.put(VIDEO_TARGET_DURATION, videoTargetDuration);
        }
    }

    /**
     * Retrieves the overall target duration of the video.
     * @return The target duration in milliseconds, or null if not set.
     */
    public Long getVideoTargetDuration() {
        String targetDuration = this.get(VIDEO_TARGET_DURATION);
        return targetDuration == null ? null : Long.parseLong(targetDuration);
    }
}
