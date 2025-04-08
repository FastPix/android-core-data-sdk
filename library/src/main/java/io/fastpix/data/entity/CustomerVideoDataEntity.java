package io.fastpix.data.entity;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The `CustomerVideoData` class is a subclass of `BaseQueryData` that represents video-related data
 * for a specific customer. This class encapsulates various properties that are tied to the video
 * experience, such as video configuration, metadata, or playback settings.
 *
 * It extends from `BaseQueryData` to inherit common functionality like handling JSON objects
 * and providing a mechanism for synchronization and data manipulation.
 */
public class CustomerVideoDataEntity extends QueryDataEntity {
    /**
     * The following static final constants represent the keys used to identify various
     * video-related attributes within the `CustomerVideoData` class. These constants will
     * be used for storing and retrieving specific information related to a customer's video
     * content, such as video metadata, encoding, content type, and other properties.
     */

    // CDN associated with the video
    public static final String VIDEO_CDN = "cn";

    // Type of content (e.g., movie, show)
    public static final String VIDEO_CONTENT_TYPE = "vdctty";

    // Duration of the video
    public static final String VIDEO_DURATION = "vddu";

    // Video encoding variant
    public static final String VIDEO_ENCODING_VARIANT = "vdeova";

    // Unique ID for the video
    public static final String VIDEO_ID = "vdid";

    // Whether the video is live
    public static final String VIDEO_IS_LIVE = "vdisli";

    // Language code of the video
    public static final String VIDEO_LANGUAGE_CODE = "vdlncd";

    // Producer of the video
    public static final String VIDEO_PRODUCER = "vdpd";

    // Series the video belongs to
    public static final String VIDEO_SERIES = "vdsr";

    // Type of video stream (e.g., HLS, DASH)
    public static final String VIDEO_STREAM_TYPE = "vdsmty";

    // Title of the video
    public static final String VIDEO_TITLE = "vdtt";

    // Variant ID for the video
    public static final String VIDEO_VARIANT_ID = "vdvaid";

    // Variant name of the video
    public static final String VIDEO_VARIANT_NAME = "vdvana";

    // Source URL of the video content
    public static final String VIDEO_SOURCE_URL = "vdsour";

    // Any experiments associated with the video
    public static final String VIDEO_EXPERIMENTS = "vdes";

    // A list of keys related to Customer Video Data
//    public static final List<String> keysCVD;
    public static final List<String> keysCVD = Collections.unmodifiableList(Arrays.asList(
            VIDEO_CDN, VIDEO_CONTENT_TYPE, VIDEO_DURATION, VIDEO_ENCODING_VARIANT, VIDEO_ID,
            VIDEO_IS_LIVE, VIDEO_LANGUAGE_CODE, VIDEO_PRODUCER, VIDEO_SERIES, VIDEO_STREAM_TYPE,
            VIDEO_TITLE, VIDEO_VARIANT_ID, VIDEO_VARIANT_NAME, VIDEO_SOURCE_URL
    ));
    /**
     * Default constructor for the `CustomerVideoData` class.
     * Initializes the object with default values.
     */
    public CustomerVideoDataEntity() {
    }

    /**
     * Syncs the `CustomerVideoData` object.
     * Currently, this method does not perform any specific synchronization tasks.
     */
    public void sync() {
    }

    /**
     * Sets the video CDN (Content Delivery Network) value for this `CustomerVideoData` object.
     *
     * @param videoCdn The CDN value to be set. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoCdn(String videoCdn) throws JSONException {
        if (videoCdn != null) {
            this.put(VIDEO_CDN, videoCdn);
        }
    }

    /**
     * Gets the video CDN (Content Delivery Network) value from this `CustomerVideoData` object.
     *
     * @return The video CDN value, or `null` if it is not set.
     */
    public String getVideoCdn() {
        return this.get(VIDEO_CDN);
    }

    /**
     * Sets the video content type for this `CustomerVideoData` object.
     *
     * @param videoContentType The content type of the video to be set. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoContentType(String videoContentType) throws JSONException {
        if (videoContentType != null) {
            this.put(VIDEO_CONTENT_TYPE, videoContentType);
        }
    }

    /**
     * Gets the video content type from this `CustomerVideoData` object.
     *
     * @return The video content type value, or `null` if it is not set.
     */
    public String getVideoContentType() {
        return this.get(VIDEO_CONTENT_TYPE);
    }

    /**
     * Sets the video duration for this `CustomerVideoData` object.
     *
     * @param videoDuration The duration of the video in seconds. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoDuration(Long videoDuration) throws JSONException {
        if (videoDuration != null) {
            this.put(VIDEO_DURATION, videoDuration);
        }
    }

    /**
     * Gets the video duration from this `CustomerVideoData` object.
     *
     * @return The video duration in seconds, or `null` if the duration is not set.
     *         If the value exists, it returns a hardcoded `0L` (Long) instead of the actual value.
     */
    public Long getVideoDuration() {

        return this.get(VIDEO_DURATION) == null ? null : 0L;
    }

    /**
     * Sets the video encoding variant for this `CustomerVideoData` object.
     *
     * @param videoEncodingVariant The encoding variant of the video to be set. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoEncodingVariant(String videoEncodingVariant) throws JSONException {

        if (videoEncodingVariant != null) {
            this.put(VIDEO_ENCODING_VARIANT, videoEncodingVariant);
        }
    }

    /**
     * Gets the video encoding variant from this `CustomerVideoData` object.
     *
     * @return The video encoding variant value, or `null` if it is not set.
     */
    public String getVideoEncodingVariant() {
        return this.get(VIDEO_ENCODING_VARIANT);
    }

    /**
     * Sets the video ID for this `CustomerVideoData` object.
     *
     * @param videoId The ID of the video to be set. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoId(String videoId) throws JSONException {
        if (videoId != null) {
            this.put(VIDEO_ID, videoId);
        }
    }

    /**
     * Gets the video ID from this `CustomerVideoData` object.
     *
     * @return The video ID, or `null` if the video ID is not set.
     */
    public String getVideoId() {
        return this.get(VIDEO_ID);
    }

    /**
     * Sets the video live status for this `CustomerVideoData` object.
     *
     * @param videoIsLive A Boolean indicating whether the video is live or not. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoIsLive(Boolean videoIsLive) throws JSONException {
        if (videoIsLive != null) {
            this.put(VIDEO_IS_LIVE, videoIsLive);
        }
    }

    /**
     * Gets the video live status from this `CustomerVideoData` object.
     *
     * @return A Boolean indicating whether the video is live, or `null` if not set.
     */
    public Boolean getVideoIsLive() {
        String videoIsLive = this.get(VIDEO_IS_LIVE);
        return videoIsLive == null ? null : Boolean.parseBoolean(videoIsLive);
    }

    /**
     * Sets the language code for this video in the `CustomerVideoData` object.
     *
     * @param videoLanguageCode The language code of the video (e.g., "en" for English). If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoLanguageCode(String videoLanguageCode) throws JSONException {
        if (videoLanguageCode != null) {
            this.put(VIDEO_LANGUAGE_CODE, videoLanguageCode);
        }
    }

    /**
     * Gets the video language code from this `CustomerVideoData` object.
     *
     * @return The video language code, or `null` if the language code is not set.
     */
    public String getVideoLanguageCode() {
        return this.get(VIDEO_LANGUAGE_CODE);
    }

    /**
     * Sets the video producer for this `CustomerVideoData` object.
     *
     * @param videoProducer The name of the video producer. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoProducer(String videoProducer) throws JSONException {
        if (videoProducer != null) {
            this.put(VIDEO_PRODUCER, videoProducer);
        }
    }

    /**
     * Gets the video producer from this `CustomerVideoData` object.
     *
     * @return The video producer, or `null` if the producer is not set.
     */
    public String getVideoProducer() {
        return this.get(VIDEO_PRODUCER);
    }

    /**
     * Sets the video series name for this `CustomerVideoData` object.
     *
     * @param videoSeries The name of the video series. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoSeries(String videoSeries) throws JSONException {
        if (videoSeries != null) {
            this.put(VIDEO_SERIES, videoSeries);
        }
    }

    /**
     * Gets the video series from this `CustomerVideoData` object.
     *
     * @return The video series, or `null` if the series is not set.
     */
    public String getVideoSeries() {
        return this.get(VIDEO_SERIES);
    }

    /**
     * Sets the video stream type for this `CustomerVideoData` object.
     *
     * @param videoStreamType The type of the video stream (e.g., "LIVE", "VOD"). If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoStreamType(String videoStreamType) throws JSONException {

        if (videoStreamType != null) {
            this.put(VIDEO_STREAM_TYPE, videoStreamType);
        }
    }

    /**
     * Gets the video stream type from this `CustomerVideoData` object.
     *
     * @return The video stream type, or `null` if the stream type is not set.
     */
    public String getVideoStreamType() {
        return this.get(VIDEO_STREAM_TYPE);
    }

    /**
     * Sets the video title for this `CustomerVideoData` object.
     *
     * @param videoTitle The title of the video. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoTitle(String videoTitle) throws JSONException {
        if (videoTitle != null) {
            this.put(VIDEO_TITLE, videoTitle);
        }
    }

    /**
     * Gets the video title from this `CustomerVideoData` object.
     *
     * @return The video title, or `null` if the title is not set.
     */
    public String getVideoTitle() {
        return this.get(VIDEO_TITLE);
    }

    /**
     * Sets the video variant ID for this `CustomerVideoData` object.
     *
     * @param videoVariantId The ID of the video variant (e.g., the specific version of the video). If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoVariantId(String videoVariantId) throws JSONException {
        if (videoVariantId != null) {
            this.put(VIDEO_VARIANT_ID, videoVariantId);
        }
    }

    /**
     * Gets the video variant ID from this `CustomerVideoData` object.
     *
     * @return The video variant ID, or `null` if the variant ID is not set.
     */
    public String getVideoVariantId() {
        return this.get(VIDEO_VARIANT_ID);
    }

    /**
     * Sets the video variant name for this `CustomerVideoData` object.
     *
     * @param videoVariantName The name of the video variant. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoVariantName(String videoVariantName) throws JSONException {
        if (videoVariantName != null) {
            this.put(VIDEO_VARIANT_NAME, videoVariantName);
        }
    }

    /**
     * Gets the video variant name from this `CustomerVideoData` object.
     *
     * @return The video variant name, or `null` if the variant name is not set.
     */
    public String getVideoVariantName() {
        return this.get(VIDEO_VARIANT_NAME);
    }

    /**
     * Sets the video source URL for this `CustomerVideoData` object.
     *
     * @param videoSourceUrl The URL of the video source. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoSourceUrl(String videoSourceUrl) throws JSONException {
        if (videoSourceUrl != null) {
            this.put(VIDEO_SOURCE_URL, videoSourceUrl);
        }
    }

    /**
     * Gets the video source URL from this `CustomerVideoData` object.
     *
     * @return The video source URL, or `null` if the URL is not set.
     */
    public String getVideoSourceUrl() {
        return this.get(VIDEO_SOURCE_URL);
    }

    /**
     * Gets the video experiments data from this `CustomerVideoData` object.
     *
     * @return The video experiments data, or `null` if no experiments are set.
     */
    public String getVideoExperiments() {
        return this.get(VIDEO_EXPERIMENTS);
    }

    /**
     * Sets the video experiments data for this `CustomerVideoData` object.
     *
     * @param videoExperiments The experiments data related to the video. If `null`, the field will not be updated.
     * @throws JSONException If there is an error during the JSON operation.
     */
    public void setVideoExperiments(String videoExperiments) throws JSONException {
        if (videoExperiments != null) {
            this.put(VIDEO_EXPERIMENTS, videoExperiments);
        }
    }


    // Static initialization block to populate the keys list
    /*static {
        List<String> keys = new ArrayList<>();
        keys.add(VIDEO_CDN);
        keys.add(VIDEO_CONTENT_TYPE);
        keys.add(VIDEO_DURATION);
        keys.add(VIDEO_ENCODING_VARIANT);
        keys.add(VIDEO_ID);
        keys.add(VIDEO_IS_LIVE);
        keys.add(VIDEO_LANGUAGE_CODE);
        keys.add(VIDEO_PRODUCER);
        keys.add(VIDEO_SERIES);
        keys.add(VIDEO_STREAM_TYPE);
        keys.add(VIDEO_TITLE);
        keys.add(VIDEO_VARIANT_ID);
        keys.add(VIDEO_VARIANT_NAME);
        keys.add(VIDEO_SOURCE_URL);
        keysCVD = Collections.unmodifiableList(keys);
    }*/
}
