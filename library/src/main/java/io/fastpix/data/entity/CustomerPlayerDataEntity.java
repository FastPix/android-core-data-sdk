package io.fastpix.data.entity;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the player-related data for a customer.
 *
 * This class extends from `BaseQueryData` and provides methods to manage
 * and manipulate data specific to the customer’s player information. It allows
 * retrieval, modification, and synchronization of player-related data in a JSON format.
 *
 * The `CustomerPlayerData` class encapsulates the data and ensures that any
 * updates or changes to player data are synchronized and managed within the
 * base query data structure.
 */
public class CustomerPlayerDataEntity extends QueryDataEntity {

    // Key for the experiment name associated with the player
    public static final String EXPERIMENT_NAME = "exna";

    // Key representing the page type where the player is embedded
    public static final String PAGE_TYPE = "paty";

    // Key for the time when the player was initialized
    public static final String PLAYER_INIT_TIME = "plitti";

    // Key representing the player’s name
    public static final String PLAYER_NAME = "plna";

    // Key representing the version of the player
    public static final String PLAYER_VERSION = "plvn";

    // Key for the environment ID or session ID related to the player
    public static final String ENV_KEY = "wsid";

    // Key representing the sub-property ID (likely related to a content or player feature)
    public static final String SUB_PROPERTY_ID = "suppid";

    // Key for the viewer's user ID associated with the player
    public static final String VIEWER_USER_ID = "viusid";

    // Key indicating if the player should autoplay
    public static final String PLAYER_AUTOPLAY_ON = "plauon";

    // A list of keys related to customer player data
//    public static final List<String> keysCPD;

    public static final List<String> keysCPD = Collections.unmodifiableList(Arrays.asList(
            EXPERIMENT_NAME,
            PAGE_TYPE,
            PLAYER_INIT_TIME,
            PLAYER_NAME,
            PLAYER_VERSION,
            ENV_KEY,
            SUB_PROPERTY_ID,
            VIEWER_USER_ID,
            PLAYER_AUTOPLAY_ON
    ));
    // Default constructor
    public CustomerPlayerDataEntity() {
    }

    /**
     * Sets the autoplay setting for the player.
     *
     * @param autoPlayOn A Boolean indicating whether autoplay is enabled (true) or disabled (false).
     * @throws JSONException If there is an error while adding the data to the JSON object.
     */
    public void setAutoPlayOn(Boolean autoPlayOn) throws JSONException {
        if (autoPlayOn != null) {
            this.put(PLAYER_AUTOPLAY_ON, autoPlayOn);
        }
    }

    /**
     * Retrieves the current autoplay setting for the player.
     *
     * @return A Boolean indicating whether autoplay is enabled (true) or disabled (false), or null if not set.
     */
    public Boolean isAutoPlayOn() {
        String isAutoPlayOn = this.get(PLAYER_AUTOPLAY_ON);
        return isAutoPlayOn == null ? null : Boolean.parseBoolean(isAutoPlayOn);
    }

    /**
     * Synchronizes the data in the player configuration. Currently, this method is empty and could be
     * extended to implement specific synchronization logic in the future.
     */
    public void sync() {
    }

    /**
     * Sets the experiment name associated with the player's configuration.
     *
     * @param experimentName A string representing the name of the experiment.
     * @throws JSONException If there is an error while adding the data to the JSON object.
     */
    public void setExperimentName(String experimentName) throws JSONException {
        if (experimentName != null) {
            this.put(EXPERIMENT_NAME, experimentName);
        }
    }

    /**
     * Retrieves the experiment name associated with the player's configuration.
     *
     * @return A string representing the experiment name, or null if not set.
     */
    public String getExperimentName() {
        return this.get(EXPERIMENT_NAME);
    }


    /**
     * Sets the page type for the player's configuration.
     *
     * @param pageType A string representing the type of the page where the player is embedded (e.g., "home", "details").
     * @throws JSONException If there is an error while adding the data to the JSON object.
     */
    public void setPageType(String pageType) throws JSONException {
        if (pageType != null) {
            this.put(PAGE_TYPE, pageType);
        }
    }

    /**
     * Retrieves the page type for the player's configuration.
     *
     * @return A string representing the page type, or null if not set.
     */
    public String getPageType() {
        return this.get(PAGE_TYPE);
    }

    /**
     * Sets the player initialization time, which could represent the timestamp of when the player was initialized.
     *
     * @param playerInitTime A long representing the initialization time (in milliseconds).
     * @throws JSONException If there is an error while adding the data to the JSON object.
     */
    public void setPlayerInitTime(Long playerInitTime) throws JSONException {
        if (playerInitTime != null) {
            this.put(PLAYER_INIT_TIME, playerInitTime);
        }
    }

    /**
     * Retrieves the player initialization time from the player's configuration.
     *
     * @return A Long representing the player initialization time in milliseconds, or null if not set.
     */
    public Long getPlayerInitTime() {
        String playerInitTime = this.get(PLAYER_INIT_TIME);
        return playerInitTime == null ? null : Long.parseLong(playerInitTime);
    }

    /**
     * Sets the player name for the player's configuration.
     * The player name could be a name or identifier for the player used in the application.
     *
     * @param playerName A string representing the name of the player.
     * @throws JSONException If there is an error while adding the data to the JSON object.
     */
    public void setPlayerName(String playerName) throws JSONException {
        if (playerName != null) {
            this.put(PLAYER_NAME, playerName);
        }
    }

    /**
     * Retrieves the player name from the player's configuration.
     *
     * @return A string representing the player's name, or null if not set.
     */
    public String getPlayerName() {
        return this.get(PLAYER_NAME);
    }

    /**
     * Sets the player version for the player's configuration.
     * The player version could represent the version of the player in use (e.g., "1.0.0").
     *
     * @param playerVersion A string representing the version of the player.
     * @throws JSONException If there is an error while adding the data to the JSON object.
     */
    public void setPlayerVersion(String playerVersion) throws JSONException {
        if (playerVersion != null) {
            this.put(PLAYER_VERSION, playerVersion);
        }
    }

    /**
     * Retrieves the player version from the player's configuration.
     *
     * @return A string representing the player's version, or null if not set.
     */
    public String getPlayerVersion() {
        return this.get(PLAYER_VERSION);
    }

    /**
     * Sets the workspace key for the player's configuration.
     * The workspace key could represent an environment-specific identifier or key for the player's workspace.
     *
     * @param workspaceKey A string representing the workspace key.
     * @throws JSONException If there is an error while adding the data to the JSON object.
     */
    public void setWorkspaceKey(String workspaceKey) throws JSONException {
        if (workspaceKey != null) {
            this.put(ENV_KEY, workspaceKey);
        }
    }

    /**
     * Retrieves the workspace key from the player's configuration.
     *
     * @return A string representing the workspace key, or null if not set.
     */
    public String getWorkspaceKey() {
        return this.get(ENV_KEY);
    }

    /**
     * Sets the property key for the player's configuration.
     * The property key could represent an identifier for a specific property or environment setting related to the player.
     *
     * @param propertyKey A string representing the property key.
     * @throws JSONException If there is an error while adding the data to the JSON object.
     */
    public void setPropertyKey(String propertyKey) throws JSONException {
        if (propertyKey != null) {
            this.put(ENV_KEY, propertyKey);
        }
    }

    /**
     * Retrieves the property key from the player's configuration.
     *
     * @return A string representing the property key, or null if not set.
     */
    public String getPropertyKey() {
        return this.get(ENV_KEY);
    }

    /**
     * Sets the sub-property ID for the player's configuration.
     * The sub-property ID could be a specific identifier within a property for the player.
     *
     * @param subPropertyId A string representing the sub-property ID.
     * @throws JSONException If there is an error while adding the data to the JSON object.
     */
    public void setSubPropertyId(String subPropertyId) throws JSONException {
        if (subPropertyId != null) {
            this.put(SUB_PROPERTY_ID, subPropertyId);
        }
    }

    /**
     * Retrieves the sub-property ID from the player's configuration.
     *
     * @return A string representing the sub-property ID, or null if not set.
     */
    public String getSubPropertyId() {
        return this.get(SUB_PROPERTY_ID);
    }

    /**
     * Sets the viewer user ID for the player's configuration.
     * The viewer user ID could represent a unique identifier for the user viewing the content.
     *
     * @param viewerUserId A string representing the viewer user ID.
     * @throws JSONException If there is an error while adding the data to the JSON object.
     */
    public void setViewerUserId(String viewerUserId) throws JSONException {
        if (viewerUserId != null) {
            this.put(VIEWER_USER_ID, viewerUserId);
        }
    }

    /**
     * Retrieves the viewer user ID from the player's configuration.
     *
     * @return A string representing the viewer user ID, or null if not set.
     */
    public String getViewerUserId() {
        return this.get(VIEWER_USER_ID);
    }



    // Static initialization block to populate the keys list
  /*  static {
        List<String> keys = new ArrayList<>();
        keys.add(EXPERIMENT_NAME);
        keys.add(PAGE_TYPE);
        keys.add(PLAYER_INIT_TIME);
        keys.add(PLAYER_NAME);
        keys.add(PLAYER_VERSION);
        keys.add(ENV_KEY);
        keys.add(SUB_PROPERTY_ID);
        keys.add(VIEWER_USER_ID);
        keysCPD = Collections.unmodifiableList(keys);
    }*/
}
