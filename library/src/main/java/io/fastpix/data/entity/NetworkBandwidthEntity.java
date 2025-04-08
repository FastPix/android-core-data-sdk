package io.fastpix.data.entity;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// BandwidthMetricData class is used to store and handle bandwidth-related metrics for video requests
public class NetworkBandwidthEntity extends QueryDataEntity {
    // Constant keys representing various fields related to the bandwidth metrics
    public static final String REQUEST_EVENT_TYPE = "rqevty";
    public static final String REQUEST_START = "rqst";
    public static final String REQUEST_RESPONSE_START = "rqrpst";
    public static final String REQUEST_RESPONSE_END = "rqrped";
    public static final String REQUEST_LATENCY = "rqlt";
    public static final String REQUEST_THROUGHPUT = "rqth";
    public static final String REQUEST_BYTES_LOADED = "rqbyld";
    public static final String REQUEST_TYPE = "rqty";
    public static final String REQUEST_RESPONSE_HEADERS = "rqrphs";
    public static final String REQUEST_HOSTNAME = "rqhn";
    public static final String REQUEST_MEDIA_DURATION = "rqmedu";
    public static final String REQUEST_CURRENT_LEVEL = "rqcule";
    public static final String REQUEST_MEDIA_START_TIME = "rqmestti";
    public static final String REQUEST_VIDEO_WIDTH = "rqvdwt";
    public static final String REQUEST_VIDEO_HEIGHT = "rqvdht";
    public static final String REQUEST_ERROR = "rqer";
    public static final String REQUEST_URL = "rqur";
    public static final String REQUEST_ERROR_TEXT = "rqerte";
    public static final String REQUEST_ERROR_CODE = "rqercd";
    public static final String REQUEST_LABELED_BITRATE = "rqlbbi";
    public static final String REQUEST_RENDITION_LISTS = "rqrnls";
    public static final String REQUEST_CANCEL = "rqca";
    public static final String REQUEST_ID = "rqid";

    public static final List<String> KEYS_BWMD = Collections.unmodifiableList(Arrays.asList(
            REQUEST_EVENT_TYPE, REQUEST_START, REQUEST_RESPONSE_START, REQUEST_RESPONSE_END, REQUEST_LATENCY,
            REQUEST_THROUGHPUT, REQUEST_BYTES_LOADED, REQUEST_TYPE, REQUEST_RESPONSE_HEADERS, REQUEST_HOSTNAME,
            REQUEST_MEDIA_DURATION, REQUEST_CURRENT_LEVEL, REQUEST_MEDIA_START_TIME, REQUEST_VIDEO_WIDTH,
            REQUEST_VIDEO_HEIGHT, REQUEST_ERROR, REQUEST_URL, REQUEST_ERROR_TEXT, REQUEST_ERROR_CODE,
            REQUEST_LABELED_BITRATE, REQUEST_RENDITION_LISTS, REQUEST_CANCEL, REQUEST_ID
    ));

    // Default constructor for BandwidthMetricData class
    public NetworkBandwidthEntity() {
    }

    // Placeholder method for synchronization logic (currently not implemented)
    public void sync() {
    }

    // Setters and Getters for each of the BandwidthMetricData fields
    // These methods set the values in the JSON object and retrieve them from it
    public void setRequestEventType(String requestEventType) throws JSONException {
        if (requestEventType != null) {
            this.put(REQUEST_EVENT_TYPE, requestEventType);
        }
    }

    // Getter method for the 'REQUEST_EVENT_TYPE' field. Retrieves the value associated with the key.
    public String getRequestEventType() {
        return this.get(REQUEST_EVENT_TYPE);
    }

    // Setter method for the 'REQUEST_START' field. Puts the provided 'requestStart' value into the data storage if it's not null.
    public void setRequestStart(Long requestStart) throws JSONException {
        if (requestStart != null) {
            this.put(REQUEST_START, requestStart);
        }
    }

    // Getter method for the 'REQUEST_START' field. Retrieves and parses the value associated with the 'REQUEST_START' key into a Long.
    public Long getRequestStart() {
        String requestStart = this.get(REQUEST_START);
        return requestStart == null ? null : Long.parseLong(requestStart);
    }

    // More setter and getter methods for each of the other fields...
    // Method to set the request response headers as a map of key-value pairs
    public void setRequestResponseStart(Long requestResponseStart) {
        if (requestResponseStart != null) {
            try {
                this.put(REQUEST_RESPONSE_START, requestResponseStart);
            } catch (JSONException e) {
                Log.e("JSONException", e.toString());
            }
        }
    }

    // Getter method for the 'REQUEST_RESPONSE_START' field. Retrieves and parses the value associated with the 'REQUEST_RESPONSE_START' key into a Long.
    public Long getRequestResponseStart() {
        String requestResponseStart = this.get(REQUEST_RESPONSE_START);
        return requestResponseStart == null ? null : Long.parseLong(requestResponseStart);
    }

    // Setter method for the 'REQUEST_RESPONSE_END' field. Puts the provided 'requestResponseEnd' value into the data storage if it's not null.
    public void setRequestResponseEnd(Long requestResponseEnd) throws JSONException {
        if (requestResponseEnd != null) {
            this.put(REQUEST_RESPONSE_END, requestResponseEnd);
        }
    }

    // Getter method for the 'REQUEST_RESPONSE_END' field. Retrieves and parses the value associated with the 'REQUEST_RESPONSE_END' key into a Long.
    public Long getRequestResponseEnd() {
        String requestResponseEnd = this.get(REQUEST_RESPONSE_END);
        return requestResponseEnd == null ? null : Long.parseLong(requestResponseEnd);
    }

    // Setter method for the 'REQUEST_LATENCY' field. Puts the provided 'requestLatency' value into the data storage if it's not null.
    public void setRequestLatency(Long requestLatency) throws JSONException {
        if (requestLatency != null) {
            this.put(REQUEST_LATENCY, requestLatency);
        }
    }

    // Getter method for the 'REQUEST_LATENCY' field. Retrieves and parses the value associated with the 'REQUEST_LATENCY' key into a Long.
    public Long getRequestLatency() {
        String requestLatency = this.get(REQUEST_LATENCY);
        return requestLatency == null ? null : Long.parseLong(requestLatency);
    }

    // Setter method for the 'REQUEST_THROUGHPUT' field. Puts the provided 'requestThroughput' value into the data storage if it's not null.
    public void setRequestThroughput(Long requestThroughput) throws JSONException {
        if (requestThroughput != null) {
            this.put(REQUEST_THROUGHPUT, requestThroughput);
        }
    }

    // Getter method for the 'REQUEST_THROUGHPUT' field. Retrieves and parses the value associated with the 'REQUEST_THROUGHPUT' key into a Long.
    public Long getRequestThroughput() {
        String requestThroughput = this.get(REQUEST_THROUGHPUT);
        return requestThroughput == null ? null : Long.parseLong(requestThroughput);
    }

    // Setter method for the 'REQUEST_BYTES_LOADED' field. Puts the provided 'requestBytesLoaded' value into the data storage if it's not null.
    public void setRequestBytesLoaded(Long requestBytesLoaded) throws JSONException {
        if (requestBytesLoaded != null) {
            this.put(REQUEST_BYTES_LOADED, requestBytesLoaded);
        }
    }

    // Getter method for the 'REQUEST_BYTES_LOADED' field. Retrieves and parses the value associated with the 'REQUEST_BYTES_LOADED' key into a Long.
    public Long getRequestBytesLoaded() {
        String requestBytesLoaded = this.get(REQUEST_BYTES_LOADED);
        return requestBytesLoaded == null ? null : Long.parseLong(requestBytesLoaded);
    }

    // Setter method for the 'REQUEST_TYPE' field. Puts the provided 'requestType' value into the data storage if it's not null.
    public void setRequestType(String requestType) throws JSONException {
        if (requestType != null) {
            this.put(REQUEST_TYPE, requestType);
        }
    }

    // Getter method for the 'REQUEST_TYPE' field. Retrieves the value associated with the 'REQUEST_TYPE' key.
    public String getRequestType() {
        return this.get(REQUEST_TYPE);
    }

    // Setter method for the 'REQUEST_RESPONSE_HEADERS' field. Converts the provided 'requestResponseHeaders' map
    // into a JSONObject and stores it in the data storage under the 'REQUEST_RESPONSE_HEADERS' key.
    // If the 'requestResponseHeaders' map is null or empty, no action is taken.
    public void setRequestResponseHeaders(Map<String, String> requestResponseHeaders) throws JSONException {
        if (requestResponseHeaders != null && !requestResponseHeaders.isEmpty()) {
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, String> entry : requestResponseHeaders.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
            this.jsonObject.put(REQUEST_RESPONSE_HEADERS, jsonObject);
        }
    }

    // Method to get the request response headers as a map
    public Map<String, String> getRequestResponseHeaders() throws JSONException {
        Map<String, String> headers = new HashMap<>();
        if (jsonObject.has(REQUEST_RESPONSE_HEADERS)) {
            JSONObject headerObject = jsonObject.getJSONObject(REQUEST_RESPONSE_HEADERS);
            JSONArray keys = headerObject.names();
            if (keys != null) {
                for (int i = 0; i < keys.length(); i++) {
                    String key = keys.getString(i);
                    headers.put(key, headerObject.getString(key));
                }
            }
        }
        return headers;
    }

    // Setter method for the 'REQUEST_HOSTNAME' field. Stores the provided 'requestHostName' value if it is not null.
    public void setRequestHostName(String requestHostName) throws JSONException {
        if (requestHostName != null) {
            this.put(REQUEST_HOSTNAME, requestHostName);
        }
    }

    // Getter method for the 'REQUEST_HOSTNAME' field. Retrieves the value stored under the 'REQUEST_HOSTNAME' key.
    public String getRequestHostName() {
        return this.get(REQUEST_HOSTNAME);
    }

    // Setter method for the 'REQUEST_MEDIA_DURATION' field. Stores the provided 'requestMediaDuration' value if it is not null.
    public void setRequestMediaDuration(Long requestMediaDuration) throws JSONException {
        if (requestMediaDuration != null) {
            this.put(REQUEST_MEDIA_DURATION, requestMediaDuration);
        }
    }

    // Getter method for the 'REQUEST_MEDIA_DURATION' field. Retrieves the value stored under the 'REQUEST_MEDIA_DURATION' key
    // and converts it to a Long. Returns null if no value is present.
    public Long getRequestMediaDuration() {
        String mediaDuration = this.get(REQUEST_MEDIA_DURATION);
        return mediaDuration == null ? null : Long.parseLong(mediaDuration);
    }

    // Setter method for the 'REQUEST_CURRENT_LEVEL' field. Stores the provided 'requestCurrentLevel' value if it is not null.
    public void setRequestCurrentLevel(Integer requestCurrentLevel) throws JSONException {
        if (requestCurrentLevel != null) {
            this.put(REQUEST_CURRENT_LEVEL, requestCurrentLevel);
        }
    }

    // Getter method for the 'REQUEST_CURRENT_LEVEL' field. Retrieves the value stored under the 'REQUEST_CURRENT_LEVEL' key
    // and converts it to an Integer. Returns null if no value is present.
    public Integer getRequestCurrentLevel() {
        String requestCurrentLevel = this.get(REQUEST_CURRENT_LEVEL);
        return requestCurrentLevel == null ? null : Integer.parseInt(requestCurrentLevel);
    }

    // Setter method for the 'REQUEST_MEDIA_START_TIME' field. Stores the provided 'requestMediaStartTime' value if it is not null.
    public void setRequestMediaStartTime(Long requestMediaStartTime) throws JSONException {
        if (requestMediaStartTime != null) {
            this.put(REQUEST_MEDIA_START_TIME, requestMediaStartTime);
        }
    }

    // Getter method for the 'REQUEST_MEDIA_START_TIME' field. Retrieves the value stored under the 'REQUEST_MEDIA_START_TIME' key
    // and converts it to a Long. Returns null if no value is present.
    public Long getRequestMediaStartTime() {
        String requestMediaStartTime = this.get(REQUEST_MEDIA_START_TIME);
        return requestMediaStartTime == null ? null : Long.parseLong(requestMediaStartTime);
    }

    // Setter method for the 'REQUEST_VIDEO_WIDTH' field. Stores the provided 'requestVideoWidth' value if it is not null.
    public void setRequestVideoWidth(Integer requestVideoWidth) throws JSONException {
        if (requestVideoWidth != null) {
            this.put(REQUEST_VIDEO_WIDTH, requestVideoWidth);
        }
    }

    // Getter method for the 'REQUEST_VIDEO_WIDTH' field. Retrieves the value stored under the 'REQUEST_VIDEO_WIDTH' key
    // and converts it to an Integer. Returns null if no value is present.
    public Integer getRequestVideoWidth() {
        String requestVideoWidth = this.get(REQUEST_VIDEO_WIDTH);
        return requestVideoWidth == null ? null : Integer.parseInt(requestVideoWidth);
    }

    // Setter method for the 'REQUEST_VIDEO_HEIGHT' field. Stores the provided 'requestVideoHeight' value if it is not null.
    public void setRequestVideoHeight(Integer requestVideoHeight) throws JSONException {
        if (requestVideoHeight != null) {
            this.put(REQUEST_VIDEO_HEIGHT, requestVideoHeight);
        }
    }

    // Getter method for the 'REQUEST_VIDEO_HEIGHT' field. Retrieves the value stored under the 'REQUEST_VIDEO_HEIGHT' key
    // and converts it to an Integer. Returns null if no value is present.
    public Integer getRequestVideoHeight() {
        String requestVideoHeight = this.get(REQUEST_VIDEO_HEIGHT);
        return requestVideoHeight == null ? null : Integer.parseInt(requestVideoHeight);
    }

    // Setter method for the 'REQUEST_ERROR' field. Stores the provided 'requestError' value if it is not null.
    public void setRequestError(String requestError) throws JSONException {
        if (requestError != null) {
            this.put(REQUEST_ERROR, requestError);
        }
    }

    // Getter method for the 'REQUEST_ERROR' field. Retrieves the value stored under the 'REQUEST_ERROR' key.
    public String getRequestError() {
        return this.get(REQUEST_ERROR);
    }

    // Setter method for the 'REQUEST_URL' field. Stores the provided 'requestUrl' value if it is not null.
    public void setRequestUrl(String requestUrl) throws JSONException {
        if (requestUrl != null) {
            this.put(REQUEST_URL, requestUrl);
        }
    }

    // Getter method for the 'REQUEST_URL' field. Retrieves the value stored under the 'REQUEST_URL' key.
    public String getRequestUrl() {
        return this.get(REQUEST_URL);
    }

    // Setter method for the 'REQUEST_ERROR_TEXT' field. Stores the provided 'requestErrorText' value if it is not null.
    public void setRequestErrorText(String requestErrorText) throws JSONException {
        if (requestErrorText != null) {
            this.put(REQUEST_ERROR_TEXT, requestErrorText);
        }
    }

    // Getter method for the 'REQUEST_ERROR_TEXT' field. Retrieves the value stored under the 'REQUEST_ERROR_TEXT' key.
    public String getRequestErrorText() {
        return this.get(REQUEST_ERROR_TEXT);
    }

    // Setter method for the 'REQUEST_ERROR_CODE' field. Stores the provided 'requestErrorCode' value if it is not null.
    public void setRequestErrorCode(Integer requestErrorCode) throws JSONException {
        if (requestErrorCode != null) {
            this.put(REQUEST_ERROR_CODE, requestErrorCode);
        }
    }

    // Getter method for the 'REQUEST_ERROR_CODE' field. Retrieves the value stored under the 'REQUEST_ERROR_CODE' key
    // and parses it to an Integer. Returns null if the value is not available.
    public Integer getRequestErrorCode() {
        String errorCode = this.get(REQUEST_ERROR_CODE);
        return errorCode == null ? null : Integer.parseInt(errorCode);
    }

    // Setter method for the 'REQUEST_LABELED_BITRATE' field. Stores the provided 'requestLabeledBitrate' value if it is not null.
    public void setRequestLabeledBitrate(Integer requestLabeledBitrate) throws JSONException {
        if (requestLabeledBitrate != null) {
            this.put(REQUEST_LABELED_BITRATE, requestLabeledBitrate);
        }
    }

    // Getter method for the 'REQUEST_LABELED_BITRATE' field. Retrieves the value stored under the 'REQUEST_LABELED_BITRATE' key
    // and parses it to an Integer. Returns null if the value is not available.
    public Integer getRequestLabeledBitrate() {
        String requestLabeledBitrate = this.get(REQUEST_LABELED_BITRATE);
        return requestLabeledBitrate == null ? null : Integer.parseInt(requestLabeledBitrate);
    }

    // Setter method for the 'REQUEST_RENDITION_LISTS' field. Converts the provided list of 'Rendition' objects to a JSON array
    // and stores it under the 'REQUEST_RENDITION_LISTS' key. Each 'Rendition' object is converted to a JSONObject containing
    // properties like width, height, bitrate, attrs, codec, and fps.
    public void setRequestRenditionLists(List<Rendition> requestRenditionLists) throws JSONException {
        if (requestRenditionLists != null) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            Iterator iterator = requestRenditionLists.iterator();
            // Iterate through the rendition list and create a JSONObject for each 'Rendition' object
            while (iterator.hasNext()) {
                Rendition rendition = (Rendition) iterator.next();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("width", rendition.width);
                jsonObject1.put("height", rendition.height);
                jsonObject1.put("bitrate", rendition.bitrate);
                jsonObject1.put("attrs", rendition.attrs);
                jsonObject1.put("codec", rendition.codec);
                jsonObject1.put("fps", rendition.fps);
                jsonArray.put(jsonObject1);
            }
            jsonObject.put("media", jsonArray);
            this.jsonObject.put(REQUEST_RENDITION_LISTS, jsonObject);
        }
    }


    // Getter method for the 'REQUEST_RENDITION_LISTS' field. Retrieves the stored JSON data under the 'REQUEST_RENDITION_LISTS' key,
    // parses it, and converts it back into a list of 'Rendition' objects. Each 'Rendition' object is created using the data from the JSON.
    public List<Rendition> getRequestRenditionLists() throws JSONException {
        // Create an ArrayList to hold the parsed 'Rendition' objects
        ArrayList arrayList = new ArrayList();
        // Check if the 'REQUEST_RENDITION_LISTS' field exists in the JSON object
        if (this.jsonObject.has(REQUEST_RENDITION_LISTS)) {
            // Get the 'media' array from the stored JSON object
            JSONArray jsonArray = (new JSONObject(this.jsonObject.getJSONObject(REQUEST_RENDITION_LISTS).toString())).getJSONArray("media");
            // Iterate over the 'media' array to convert each JSON object back into a 'Rendition' object
            for (int i = 0; jsonArray != null && i < jsonArray.length(); ++i) {
                // Get the JSON object at the current index
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                // Create a new 'Rendition' object and populate it with the data from the JSON object
                Rendition rendition = new Rendition();
                rendition.width = jsonObject.getInt("width");
                rendition.height = jsonObject.getInt("height");
                rendition.bitrate = jsonObject.getLong("bitrate");
                rendition.attrs = jsonObject.getString("attrs");
                rendition.codec = jsonObject.getString("codec");
                rendition.fps = jsonObject.getInt("fps");
                // Add the 'Rendition' object to the list
                arrayList.add(rendition);
            }
        }
        // Return the populated list of 'Rendition' objects
        return arrayList;
    }


    // Sets the 'REQUEST_ID' field in the JSON object if the provided string is not null.
    // The string is stored as the value for the 'REQUEST_ID' key.
    public void setRequestId(String string) throws JSONException {
        // Check if the provided string is not null
        if (string != null) {
            // Store the string value under the 'REQUEST_ID' key in the JSON object
            this.put(REQUEST_ID, string);
        }
    }

    // Retrieves the value of the 'REQUEST_ID' field from the JSON object.
    // Returns the value stored under the 'REQUEST_ID' key.
    public String getRequestId() {
        // Get and return the value stored under the 'REQUEST_ID' key
        return this.get(REQUEST_ID);
    }

    // Sets the 'REQUEST_CANCEL' field in the JSON object if the provided string is not null.
    // The string is stored as the value for the 'REQUEST_CANCEL' key.
    public void setRequestCancel(String string) throws JSONException {
        // Check if the provided string is not null
        if (string != null) {
            // Store the string value under the 'REQUEST_CANCEL' key in the JSON object
            this.put(REQUEST_CANCEL, string);
        }
    }

    // Retrieves the value of the 'REQUEST_CANCEL' field from the JSON object.
    // Returns the value stored under the 'REQUEST_CANCEL' key.
    public String getRequestCancel() {
        // Get and return the value stored under the 'REQUEST_CANCEL' key
        return this.get(REQUEST_CANCEL);
    }

    // Represents a media rendition with various properties such as resolution, frame rate, bitrate, codec, and additional attributes.
    // A rendition typically refers to a specific version of a media stream (e.g., different video qualities or formats).
    public static class Rendition {
        // Width of the media rendition in pixels (e.g., 1920 for 1080p)
        public int width;
        // Height of the media rendition in pixels (e.g., 1080 for 1080p)
        public int height;
        // Frames per second of the media rendition (e.g., 30fps, 60fps)
        public int fps;
        // Bitrate of the media rendition in bits per second (e.g., 5000000 for 5 Mbps)
        public long bitrate;
        // Codec used to encode the media rendition (e.g., "H.264", "VP9")
        public String codec;
        // Name of the rendition, possibly used to identify it (e.g., "HD", "4K")
        public String name;
        // Additional attributes related to the rendition, typically in string format
        public String attrs;

        // Default constructor, may throw JSONException if needed, but currently does nothing.
        public Rendition() throws JSONException {
        }
    }
}
