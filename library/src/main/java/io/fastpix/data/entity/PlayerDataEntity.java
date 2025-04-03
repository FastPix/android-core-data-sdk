package io.fastpix.data.entity;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The PlayerData class holds key-value pairs representing various player-related data.
 * This includes player state information, error details, playback timing, software version,
 * and other configuration settings related to the video player.
 *
 * Constants are defined for player attributes, such as:
 * - Page load time and URL
 * - Player's autoplay and fullscreen status
 * - Error codes and messages
 * - Player's height, width, and instance ID
 * - Plugin information and software version
 * - Playback-related timings such as playhead and load times
 * - Viewing stats like sequence number, view count, and program time
 * - Player's manifest and program details
 *
 * These constants are used to interact with the player, log data, or track player performance and behaviors.
 */
public class PlayerDataEntity extends QueryDataEntity {
    public static final String PAGE_LOAD_TIME = "paloti";
    public static final String PAGE_URL = "paur";
    public static final String PLAYER_AUTOPLAY_ON = "plauon";
    public static final String PLAYER_ERROR_CODE = "plercd";
    public static final String PLAYER_ERROR_MESSAGE = "plerms";
    public static final String PLAYER_HEIGHT = "plht";
    public static final String PLAYER_INSTANCE_ID = "plinid";
    public static final String PLAYER_IS_FULLSCREEN = "plisfl";
    public static final String PLAYER_IS_PAUSED = "plispu";
    public static final String PLAYER_LANGUAGE_CODE = "pllncd";
    public static final String PLAYER_LOAD_TIME = "plloti";
    public static final String PLAYER_FASTPIX_PLUGIN_NAME = "plfpsdna";//plfppina
    public static final String PLAYER_FASTPIX_PLUGIN_VERSION = "plfpsdvn";//plfppivn
    public static final String PLAYER_PLAYHEAD_TIME = "plphti";
    public static final String PLAYER_PRELOAD_ON = "plpron";
    public static final String PLAYER_SEQUENCE_NUMBER = "plsqnu";
    public static final String PLAYER_SOFTWARE_NAME = "plswna";
    public static final String PLAYER_SOFTWARE_VERSION = "plswvn";
    public static final String PLAYER_STARTUP_TIME = "plspti";
    public static final String PLAYER_VIEW_COUNT = "plveco";
    public static final String PLAYER_WIDTH = "plwt";
    public static final String PLAYER_PROGRAM_TIME = "plpmti";
    public static final String PLAYER_MANIFEST_NEWEST_PROGRAM_TIME = "plmfnepmti";
    public static final String PLAYER_ERROR_CONTEXT = "plercx";

    //    public static final List<String> PDD;
    public static final List<String> PDD = Collections.unmodifiableList(Arrays.asList(
            PAGE_LOAD_TIME, PAGE_URL, PLAYER_AUTOPLAY_ON, PLAYER_ERROR_CODE, PLAYER_ERROR_MESSAGE,
            PLAYER_HEIGHT, PLAYER_INSTANCE_ID, PLAYER_IS_FULLSCREEN, PLAYER_IS_PAUSED, PLAYER_LANGUAGE_CODE,
            PLAYER_LOAD_TIME, PLAYER_FASTPIX_PLUGIN_NAME, PLAYER_FASTPIX_PLUGIN_VERSION, PLAYER_PLAYHEAD_TIME,
            PLAYER_PRELOAD_ON, PLAYER_SEQUENCE_NUMBER, PLAYER_SOFTWARE_NAME, PLAYER_SOFTWARE_VERSION,
            PLAYER_STARTUP_TIME, PLAYER_VIEW_COUNT, PLAYER_WIDTH, PLAYER_PROGRAM_TIME,
            PLAYER_MANIFEST_NEWEST_PROGRAM_TIME, PLAYER_ERROR_CONTEXT
    ));

    // Default constructor
    public PlayerDataEntity() {
    }

    // Synchronization method (can be used for data syncing logic)
    public void sync() {
    }

    /**
     * Sets the page load time.
     * The page load time is the time taken for the player page to load.
     * This value is stored as a Long representing milliseconds.
     *
     * @param pageLoadTime The page load time to set.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPageLoadTime(Long pageLoadTime) throws JSONException {
        if (pageLoadTime != null) {
            this.put(PAGE_LOAD_TIME, pageLoadTime);
        }
    }

    /**
     * Gets the page load time.
     * This method retrieves the page load time from the stored data.
     * The value is returned as a Long representing milliseconds or null if not set.
     *
     * @return The page load time or null if not set.
     */
    public Long getPageLoadTime() {
        String stPageLoadTime = this.get(PAGE_LOAD_TIME);
        return stPageLoadTime == null ? null : Long.parseLong(stPageLoadTime);
    }

    /**
     * Sets the page URL.
     * This method stores the URL of the page where the player is embedded.
     * The URL is stored as a String.
     *
     * @param pageUrl The URL to set for the page.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPageUrl(String pageUrl) throws JSONException {
        if (pageUrl != null) {
            this.put(PAGE_URL, pageUrl);
        }
    }

    /**
     * Gets the page URL.
     * This method retrieves the URL of the page where the player is embedded.
     *
     * @return The page URL as a String, or null if not set.
     */
    public String getPageUrl() {
        return this.get(PAGE_URL);
    }

    /**
     * Sets the player's autoplay status.
     * This method stores whether autoplay is enabled for the player.
     * The value should be a String (e.g., "true" or "false") indicating if autoplay is turned on.
     *
     * @param playerAutoplayOn The autoplay status to set (e.g., "true" or "false").
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerAutoplayOn(Boolean playerAutoplayOn) throws JSONException {
        if (playerAutoplayOn != null) {
            this.put(PLAYER_AUTOPLAY_ON, playerAutoplayOn);
        }
    }

    /**
     * Gets the player's autoplay status.
     * This method retrieves whether autoplay is enabled for the player.
     *
     * @return The autoplay status as a String (e.g., "true" or "false").
     */
    public String getPlayerAutoplayOn() {
        return this.get(PLAYER_AUTOPLAY_ON);
    }

    /**
     * Sets the player's error code.
     * This method stores the error code related to the player's operation, if any.
     *
     * @param playerErrorCode The error code to set for the player.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerErrorCode(String playerErrorCode) throws JSONException {
        this.put(PLAYER_ERROR_CODE, playerErrorCode);
    }

    /**
     * Gets the player's error code.
     * This method retrieves the error code associated with the player's operation.
     *
     * @return The player's error code as a String, or null if not set.
     */
    public String getPlayerErrorCode() {
        return this.get(PLAYER_ERROR_CODE);
    }

    /**
     * Sets the player's error message.
     * This method stores an error message related to the player's operation if an error occurs.
     *
     * @param playerErrorMessage The error message to set.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerErrorMessage(String playerErrorMessage) throws JSONException {
        this.put(PLAYER_ERROR_MESSAGE, playerErrorMessage);
    }

    /**
     * Gets the player's error message.
     * This method retrieves the error message associated with the player's operation.
     *
     * @return The player's error message as a String, or null if not set.
     */
    public String getPlayerErrorMessage() {
        return this.get(PLAYER_ERROR_MESSAGE);
    }

    /**
     * Sets the player's height.
     * This method stores the height of the player in pixels.
     *
     * @param playerHeight The height of the player in pixels.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerHeight(Integer playerHeight) throws JSONException {
        if (playerHeight != null) {
            this.put(PLAYER_HEIGHT, playerHeight);
        }
    }

    /**
     * Gets the player's height.
     * This method retrieves the height of the player in pixels.
     *
     * @return The player's height in pixels, or null if not set.
     */
    public Integer getPlayerHeight() {
        String playerHeight = this.get(PLAYER_HEIGHT);
        return playerHeight == null ? null : Integer.parseInt(playerHeight);
    }

    /**
     * Sets the player's instance ID.
     * This method stores a unique identifier for the player's instance.
     * This could be used to track specific player sessions or instances.
     *
     * @param playerInstanceId The unique identifier for the player instance.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerInstanceId(String playerInstanceId) throws JSONException {
        if (playerInstanceId != null) {
            this.put(PLAYER_INSTANCE_ID, playerInstanceId);
        }
    }

    /**
     * Gets the player's instance ID.
     * This method retrieves the unique identifier for the player's instance.
     *
     * @return The player's instance ID as a String, or null if not set.
     */
    public String getPlayerInstanceId() {
        return this.get(PLAYER_INSTANCE_ID);
    }

    /**
     * Sets whether the player is in fullscreen mode.
     * This method stores the fullscreen status of the player.
     *
     * @param playerIsFullscreen A String representing whether the player is in fullscreen ("true" or "false").
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerIsFullscreen(String playerIsFullscreen) throws JSONException {
        if (playerIsFullscreen != null) {
            this.put(PLAYER_IS_FULLSCREEN, playerIsFullscreen);
        }
    }

    /**
     * Gets the player's fullscreen status.
     * This method retrieves whether the player is currently in fullscreen mode.
     *
     * @return A String representing the fullscreen status ("true" or "false"), or null if not set.
     */
    public String getPlayerIsFullscreen() {
        return this.get(PLAYER_IS_FULLSCREEN);
    }

    /**
     * Sets the player's pause status.
     * This method stores whether the player is currently paused or not.
     *
     * @param playerIsPaused A Boolean indicating whether the player is paused (true or false).
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerIsPaused(Boolean playerIsPaused) throws JSONException {
        if (playerIsPaused != null) {
            this.put(PLAYER_IS_PAUSED, playerIsPaused);
        }
    }

    /**
     * Gets the player's pause status.
     * This method retrieves whether the player is currently paused.
     *
     * @return A Boolean representing the player's paused status, or null if not set.
     */
    public Boolean getPlayerIsPaused() {
        String playerIsPaused = this.get(PLAYER_IS_PAUSED);
        return playerIsPaused == null ? null : Boolean.parseBoolean(playerIsPaused);
    }

    /**
     * Sets the player's language code.
     * This method stores the language code in which the player is being used.
     *
     * @param playerLanguageCode A String representing the language code (e.g., "en" for English).
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerLanguageCode(String playerLanguageCode) throws JSONException {
        if (playerLanguageCode != null) {
            this.put(PLAYER_LANGUAGE_CODE, playerLanguageCode);
        }
    }

    /**
     * Gets the player's language code.
     * This method retrieves the language code in which the player is being used.
     *
     * @return A String representing the language code, or null if not set.
     */
    public String getPlayerLanguageCode() {
        return this.get(PLAYER_LANGUAGE_CODE);
    }

    /**
     * Sets the player's load time.
     * This method stores the time taken for the player to load.
     *
     * @param playerLoadTime A Long value representing the time in milliseconds.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerLoadTime(Long playerLoadTime) throws JSONException {
        if (playerLoadTime != null) {
            this.put(PLAYER_LOAD_TIME, playerLoadTime);
        }
    }

    /**
     * Gets the player's load time.
     * This method retrieves the time taken for the player to load.
     *
     * @return A Long representing the load time in milliseconds, or null if not set.
     */
    public Long getPlayerLoadTime() {
        String playerLoadTime = this.get(PLAYER_LOAD_TIME);
        return playerLoadTime == null ? null : Long.parseLong(playerLoadTime);
    }

    /**
     * Sets the name of the FastPix plugin used by the player.
     * This method stores the name of the FastPix plugin that is integrated with the player.
     *
     * @param playerFPPluginName A String representing the name of the FastPix plugin.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerFPPluginName(String playerFPPluginName) throws JSONException {
        if (playerFPPluginName != null) {
            this.put(PLAYER_FASTPIX_PLUGIN_NAME, playerFPPluginName);
        }
    }

    /**
     * Gets the name of the FastPix plugin used by the player.
     * This method retrieves the name of the FastPix plugin that is integrated with the player.
     *
     * @return A String representing the name of the FastPix plugin, or null if not set.
     */
    public String getPlayerFPPluginName() {
        return this.get(PLAYER_FASTPIX_PLUGIN_NAME);
    }

    /**
     * Sets the version of the FastPix plugin used by the player.
     * This method stores the version of the FastPix plugin that is integrated with the player.
     *
     * @param playerFPPluginVersion A String representing the version of the FastPix plugin.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerFPPluginVersion(String playerFPPluginVersion) throws JSONException {
        if (playerFPPluginVersion != null) {
            this.put(PLAYER_FASTPIX_PLUGIN_VERSION, playerFPPluginVersion);
        }
    }

    /**
     * Gets the version of the FastPix plugin used by the player.
     * This method retrieves the version of the FastPix plugin integrated with the player.
     *
     * @return A String representing the version of the FastPix plugin, or null if not set.
     */
    public String getPlayerFPPluginVersion() {
        return this.get(PLAYER_FASTPIX_PLUGIN_VERSION);
    }

    /**
     * Sets the player's playhead time.
     * This method stores the current time of the player’s playback, also known as the playhead time.
     *
     * @param playerPlayheadTime A Long representing the time in milliseconds of the player's current playhead position.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerPlayheadTime(Long playerPlayheadTime) throws JSONException {
        if (playerPlayheadTime != null) {
            this.put(PLAYER_PLAYHEAD_TIME, playerPlayheadTime);
        }
    }

    /**
     * Gets the current playhead time of the player.
     * This method retrieves the current position of the playhead in the media being played.
     *
     * @return A Long representing the current playhead time in milliseconds, or null if not set.
     */
    public Long getPlayerPlayheadTime() {
        String playerPlayheadTime = this.get(PLAYER_PLAYHEAD_TIME);
        return playerPlayheadTime == null ? null : Long.parseLong(playerPlayheadTime);
    }

    /**
     * Sets whether the player should preload content.
     * This method stores the setting to indicate whether the player should preload content before playback starts.
     *
     * @param playerPreloadOn A Boolean representing whether content should be preloaded. If true, the player will preload content.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerPreloadOn(Boolean playerPreloadOn) throws JSONException {
        if (playerPreloadOn != null) {
            this.put(PLAYER_PRELOAD_ON, playerPreloadOn);
        }
    }

    /**
     * Gets whether the player is set to preload content.
     * This method retrieves the setting that indicates whether the player should preload content before playback starts.
     *
     * @return A Boolean representing whether preloading is enabled, or null if not set.
     */
    public Boolean getPlayerPreloadOn() {
        String playerPreLoadOn = this.get(PLAYER_PRELOAD_ON);
        return playerPreLoadOn == null ? null : Boolean.parseBoolean(playerPreLoadOn);
    }

    /**
     * Sets the sequence number of the player.
     * This method stores the sequence number that represents the current sequence of content being played in the player.
     *
     * @param playerSequenceNumber An Integer representing the sequence number.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerSequenceNumber(Integer playerSequenceNumber) throws JSONException {
        if (playerSequenceNumber != null) {
            this.put(PLAYER_SEQUENCE_NUMBER, playerSequenceNumber);
        }
    }

    /**
     * Gets the current sequence number of the player.
     * This method retrieves the sequence number, which can represent the order of content being played.
     *
     * @return An Integer representing the current sequence number, or null if not set.
     */
    public Integer getPlayerSequenceNumber() {
        String plyerSequenceNumber = this.get(PLAYER_SEQUENCE_NUMBER);
        return plyerSequenceNumber == null ? null : Integer.parseInt(plyerSequenceNumber);
    }

    /**
     * Sets the name of the software running the player.
     * This method stores the name of the software that is used to run the player, such as a custom media player.
     *
     * @param playerSoftwareName A String representing the software name (e.g., "FastPixPlayer").
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerSoftwareName(String playerSoftwareName) throws JSONException {
        if (playerSoftwareName != null) {
            this.put(PLAYER_SOFTWARE_NAME, playerSoftwareName);
        }
    }

    /**
     * Gets the name of the software running the player.
     * This method retrieves the name of the software used to run the player.
     *
     * @return A String representing the software name, or null if not set.
     */
    public String getPlayerSoftwareName() {
        return this.get(PLAYER_SOFTWARE_NAME);
    }

    /**
     * Sets the version of the software running the player.
     * This method stores the version number of the software that is used to run the player.
     *
     * @param playerSoftwareVersion A String representing the software version (e.g., "1.0.0").
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerSoftwareVersion(String playerSoftwareVersion) throws JSONException {
        if (playerSoftwareVersion != null) {
            this.put(PLAYER_SOFTWARE_VERSION, playerSoftwareVersion);
        }
    }

    /**
     * Gets the version of the software running the player.
     * This method retrieves the version number of the software used to run the player.
     *
     * @return A String representing the software version, or null if not set.
     */
    public String getPlayerSoftwareVersion() {
        return this.get(PLAYER_SOFTWARE_VERSION);
    }

    /**
     * Sets the startup time of the player.
     * This method stores the time taken by the player to start, often measured in milliseconds.
     *
     * @param playerStartupTime A Long representing the startup time in milliseconds.
     * @throws JSONException If there's an error during setting the value.
     */
    public void setPlayerStartupTime(Long playerStartupTime) throws JSONException {
        if (playerStartupTime != null) {
            this.put(PLAYER_STARTUP_TIME, playerStartupTime);
        }
    }

    /**
     * Gets the startup time of the player.
     * Retrieves the time taken by the player to start, typically measured in milliseconds.
     *
     * @return A Long representing the startup time in milliseconds, or null if not set.
     */
    public Long getPlayerStartupTime() {

        String stPlayerStartupTime = this.get(PLAYER_STARTUP_TIME);
        return stPlayerStartupTime == null ? null : Long.parseLong(stPlayerStartupTime);
    }

    /**
     * Sets the number of times the player has been viewed.
     * This method stores the view count of the player, which can be used for tracking player engagement.
     *
     * @param playerViewCount An Integer representing the view count.
     * @throws JSONException If there's an error while setting the value.
     */
    public void setPlayerViewCount(Integer playerViewCount) throws JSONException {
        if (playerViewCount != null) {
            this.put(PLAYER_VIEW_COUNT, playerViewCount);
        }
    }

    /**
     * Gets the number of times the player has been viewed.
     * Retrieves the number of times the player has been viewed. This can be used for analytics and user engagement.
     *
     * @return An Integer representing the view count, or null if not set.
     */
    public Integer getPlayerViewCount() {
        String stPlayerViewCount = this.get(PLAYER_VIEW_COUNT);
        return stPlayerViewCount == null ? null : Integer.parseInt(stPlayerViewCount);
    }

    /**
     * Sets the width of the player.
     * Stores the width of the player, typically in pixels, to track its size or adjust layout settings.
     *
     * @param playerWidth An Integer representing the width of the player in pixels.
     * @throws JSONException If there's an error while setting the value.
     */
    public void setPlayerWidth(Integer playerWidth) throws JSONException {
        if (playerWidth != null) {
            this.put(PLAYER_WIDTH, playerWidth);
        }
    }

    /**
     * Gets the width of the player.
     * Retrieves the width of the player in pixels, which can be used to adjust layout or perform other size-related actions.
     *
     * @return An Integer representing the width of the player in pixels, or null if not set.
     */
    public Integer getPlayerWidth() {
        String stPlayerWidth = this.get(PLAYER_WIDTH);
        return stPlayerWidth == null ? null : Integer.parseInt(stPlayerWidth);
    }

    /**
     * Sets the program time of the player.
     * This method stores the time related to the current media program, typically used for tracking or synchronization.
     *
     * @param playerProgramTime A Long representing the program time in milliseconds.
     * @throws JSONException If there's an error while setting the value.
     */
    public void setPlayerProgramTime(Long playerProgramTime) throws JSONException {
        if (playerProgramTime != null) {
            this.put(PLAYER_PROGRAM_TIME, playerProgramTime);
        }
    }

    /**
     * Gets the program time of the player.
     * Retrieves the program time in milliseconds, which represents the current position or state of the media being played.
     *
     * @return A Long representing the program time in milliseconds, or null if not set.
     */
    public Long getPlayerProgramTime() {
        String stPlayerProgramTime = this.get(PLAYER_PROGRAM_TIME);
        return stPlayerProgramTime == null ? null : Long.parseLong(stPlayerProgramTime);
    }

    /**
     * Sets the newest program time from the player manifest.
     * This method stores the newest available program time from the player's manifest, used to track the latest media segment or content.
     *
     * @param playerManifestNewestProgramTime A Long representing the newest program time in milliseconds.
     * @throws JSONException If there's an error while setting the value.
     */
    public void setPlayerManifestNewestProgramTime(Long playerManifestNewestProgramTime) throws JSONException {
        if (playerManifestNewestProgramTime != null) {
            this.put(PLAYER_MANIFEST_NEWEST_PROGRAM_TIME, playerManifestNewestProgramTime);
        }
    }

    /**
     * Gets the newest program time from the player manifest.
     * This method retrieves the newest available program time from the player's manifest, typically used for tracking
     * the latest segment or content from the player's media stream.
     *
     * @return A Long representing the newest program time in milliseconds, or null if not set.
     */
    public Long getPlayerManifestNewestProgramTime() {
        String maifestProgramTime = this.get(PLAYER_MANIFEST_NEWEST_PROGRAM_TIME);
        return maifestProgramTime == null ? null : Long.parseLong(maifestProgramTime);
    }

    /**
     * Gets the error context for the player.
     * This method retrieves the context related to the player error, which can be used to diagnose issues related to the player
     * or track error scenarios more efficiently.
     *
     * @return A String representing the player error context, or null if not set.
     */
    public String getPlayerErrorContext() {
        return this.get(PLAYER_ERROR_CONTEXT);
    }


    /**
     * Sets the error context for the player.
     * This method stores the context related to the player error, which can help with diagnosing issues or tracking errors.
     *
     * @param playerErrorContext A String representing the error context.
     * @throws JSONException If there's an error while setting the value.
     */
    public void setPlayerErrorContext(String playerErrorContext) throws JSONException {
        if (playerErrorContext != null) {
            super.put(PLAYER_ERROR_CONTEXT, playerErrorContext);
        }
    }
}
