package io.fastpix.data.entity;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class representing environment-specific data used for debugging, session management, and version tracking
 * related to Fastpix embedded player and API.
 *
 * This class contains constants for various keys used in data, such as API versions, embed version,
 * viewer ID, and session management information. These keys are typically used to store and retrieve
 * values related to the current environment in the system.
 *
 * Each key represents a different aspect of the environment or session, such as:
 * - **DEBUG**: For debugging purposes.
 * - **FASTPIX_API_VERSION**: To store the version of the Fastpix API.
 * - **FASTPIX_EMBED_VERSION**: For storing the version of the embedded Fastpix player.
 * - **FASTPIX_EMBED**: A flag or identifier indicating whether Fastpix is embedded.
 * - **FASTPIX_VIEWER_ID**: An ID for the viewer using the Fastpix player.
 * - **SESSION_EXPIRES_TIME**: The expiration time of the session.
 * - **SESSION_ID**: The ID of the current session.
 * - **SESSION_START**: The start time of the session.
 *
 * This class likely interacts with the underlying system to track and manage these aspects, particularly
 * related to the Fastpix player integration and session-based data.
 */
public class WorkSpaceEntity extends QueryDataEntity {
    // Key for debugging information
    public static final String DEBUG = "db";

    // Key for Fastpix API version
    public static final String FASTPIX_API_VERSION = "fpaivn";

    // Key for Fastpix embed version
    public static final String FASTPIX_EMBED_VERSION = "fpemvn";

    // Key for Fastpix embed flag/ID
    public static final String FASTPIX_EMBED = "fpem";

    // Key for Fastpix viewer ID
    public static final String FASTPIX_VIEWER_ID = "fpviid";

    // Key for session expiration time
    public static final String SESSION_EXPIRES_TIME = "snepti";

    // Key for session ID
    public static final String SESSION_ID = "snid";

    // Key for session start time
    public static final String SESSION_START = "snst";

    // List of keys used in this class
//    public static final List<String> keysEV;

    public static final List<String> keysEV = Collections.unmodifiableList(Arrays.asList(
            DEBUG, FASTPIX_API_VERSION, FASTPIX_EMBED_VERSION, FASTPIX_EMBED, FASTPIX_VIEWER_ID,
            SESSION_EXPIRES_TIME, SESSION_ID, SESSION_START
    ));
    /**
     * Constructor for the EnvironmentData class. Initializes a new instance of the class.
     */
    public WorkSpaceEntity() {
    }

    public void sync() {
    }

    /**
     * Sets the value for the debug key.
     * If the debug value is not null, it stores the value associated with the DEBUG constant.
     *
     * @param debug The debug information to store.
     * @throws JSONException If there is an issue with the underlying data structure during insertion.
     */
    public void setDebug(String debug) throws JSONException {
        if (debug != null) {
            this.put(DEBUG, debug);
        }
    }

    /**
     * Gets the value associated with the DEBUG key.
     *
     * @return The debug value as a string, or null if not set.
     */
    public String getDebug() {
        return this.get(DEBUG);
    }

    /**
     * Sets the Fastpix API version.
     * If the given version is not null, it stores the version under the FASTPIX_API_VERSION key.
     *
     * @param fpapiversion The Fastpix API version to store.
     * @throws JSONException If there is an issue with the underlying data structure during insertion.
     */
    public void setFPApiVersion(String fpapiversion) throws JSONException {
        if (fpapiversion != null) {
            this.put(FASTPIX_API_VERSION, fpapiversion);
        }
    }

    /**
     * Gets the Fastpix API version.
     *
     * @return The Fastpix API version, or null if not set.
     */
    public String getFPApiVersion() {
        return this.get(FASTPIX_API_VERSION);
    }

    /**
     * Sets the Fastpix embed version.
     * If the given version is not null, it stores the version under the FASTPIX_EMBED_VERSION key.
     *
     * @param fpembedversion The Fastpix embed version to store.
     * @throws JSONException If there is an issue with the underlying data structure during insertion.
     */
    public void setFPEmbedVersion(String fpembedversion) throws JSONException {
        if (fpembedversion != null) {
            this.put(FASTPIX_EMBED_VERSION, fpembedversion);
        }
    }

    /**
     * Gets the Fastpix embed version.
     *
     * @return The Fastpix embed version, or null if not set.
     */
    public String getFPEmbedVersion() {
        return this.get(FASTPIX_EMBED_VERSION);
    }

    /**
     * Sets the Fastpix embed data.
     * If the given embed data is not null, it stores the embed data under the FASTPIX_EMBED key.
     *
     * @param fpembed The Fastpix embed data to store.
     * @throws JSONException If there is an issue with the underlying data structure during insertion.
     */
    public void setFPEmbed(String fpembed) throws JSONException {
        if (fpembed != null) {
            this.put(FASTPIX_EMBED, fpembed);
        }
    }

    /**
     * Gets the Fastpix embed data.
     *
     * @return The Fastpix embed data, or null if not set.
     */
    public String getFPEmbed() {
        return this.get(FASTPIX_EMBED);
    }

    /**
     * Sets the Fastpix viewer ID.
     * If the given viewer ID is not null, it stores the viewer ID under the FASTPIX_VIEWER_ID key.
     *
     * @param fpviewerid The Fastpix viewer ID to store.
     * @throws JSONException If there is an issue with the underlying data structure during insertion.
     */
    public void setFPViewerId(String fpviewerid) throws JSONException {
        if (fpviewerid != null) {
            this.put(FASTPIX_VIEWER_ID, fpviewerid);
        }
    }

    /**
     * Retrieves the Fastpix viewer ID.
     *
     * @return The Fastpix viewer ID, or null if not set.
     */
    public String getFPViewerId() {
        return this.get(FASTPIX_VIEWER_ID);
    }

    /**
     * Sets the session expiration time.
     * If the given expiration time is not null, it stores it under the SESSION_EXPIRES_TIME key.
     *
     * @param sessionExpires The session expiration time to store, in milliseconds.
     * @throws JSONException If there is an issue with the underlying data structure during insertion.
     */
    public void setSessionExpires(Long sessionExpires) throws JSONException {
        if (sessionExpires != null) {
            this.put(SESSION_EXPIRES_TIME, sessionExpires);
        }
    }

    /**
     * Retrieves the session expiration time.
     *
     * @return The session expiration time, or null if not set or invalid.
     */
    public Long getSessionExpires() {
        String stSessionExpires = this.get(SESSION_EXPIRES_TIME);
        return stSessionExpires == null ? null : Long.parseLong(stSessionExpires);
    }

    /**
     * Sets the session ID.
     * If the given session ID is not null, it stores it under the SESSION_ID key.
     *
     * @param sessionId The session ID to store.
     * @throws JSONException If there is an issue with the underlying data structure during insertion.
     */
    public void setSessionId(String sessionId) throws JSONException {
        if (sessionId != null) {
            this.put(SESSION_ID, sessionId);
        }
    }

    /**
     * Retrieves the session ID.
     *
     * @return The session ID, or null if not set.
     */
    public String getSessionId() {
        return this.get(SESSION_ID);
    }

    /**
     * Sets the session start time.
     * If the given start time is not null, it stores it under the SESSION_START key.
     *
     * @param sessionStart The session start time to store, in milliseconds.
     * @throws JSONException If there is an issue with the underlying data structure during insertion.
     */
    public void setSessionStart(Long sessionStart) throws JSONException {
        if (sessionStart != null) {
            this.put(SESSION_START, sessionStart);
        }
    }

    /**
     * Retrieves the session start time.
     *
     * @return The session start time, or null if not set or invalid.
     */
    public Long getSessionStart() {
        String getSEsstionStart = this.get(SESSION_START);
        return getSEsstionStart == null ? null : Long.parseLong(getSEsstionStart);
    }

    // Static initialization block to populate the keys list
  /*  static {
        List<String> keys = new ArrayList<>();
        keys.add(DEBUG);
        keys.add(FASTPIX_API_VERSION);
        keys.add(FASTPIX_EMBED_VERSION);
        keys.add(FASTPIX_EMBED);
        keys.add(FASTPIX_VIEWER_ID);
        keys.add(SESSION_EXPIRES_TIME);
        keys.add(SESSION_ID);
        keys.add(SESSION_START);
        keysEV = Collections.unmodifiableList(keys);
    }*/
}

