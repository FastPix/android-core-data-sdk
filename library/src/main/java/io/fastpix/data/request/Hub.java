package io.fastpix.data.request;

import io.fastpix.data.streaming.AbstractEventObserver;
import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.Interfaces.EventObserver;
import io.fastpix.data.streaming.MonitoredEventContract;
import io.fastpix.data.streaming.StreamingData;
import io.fastpix.data.entity.WorkSpaceEntity;
import io.fastpix.data.entity.ViewerDataEntity;

import org.json.JSONException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Core is the main class responsible for managing key functionalities and orchestrating different components
 * within the system. It may handle core business logic, data processing, or serve as a hub for other modules.
 */
public class Hub {
    private static final ConcurrentHashMap<String, PlayerEngine> CONCURRENT_HASH_MAP = new ConcurrentHashMap();
    private static EventObserver eventObserver;
    private static final WorkSpaceEntity ENVIRONMENT_DATA = new WorkSpaceEntity();
    private static final ViewerDataEntity VIEWER_DATA = new ViewerDataEntity();
    private static final StreamingSession STREAMING_SESSION = new StreamingSession();
    private static boolean aBoolean = false;

    public Hub() {
    }

    /**
     * Creates a new CorePlayer instance, initializes the session, and stores it in a concurrent map.
     *
     * @param tag            A unique identifier for the player instance.
     * @param customOptions  Custom options for player configuration.
     * @return CorePlayer    The newly created CorePlayer instance.
     * @throws JSONException If there is an error handling JSON data.
     */
    public static PlayerEngine createPlayer(String tag, CustomOptions customOptions) throws JSONException {
        if (!aBoolean) {
            STREAMING_SESSION.startSession();
            ENVIRONMENT_DATA.setFPApiVersion("1.0");
            ENVIRONMENT_DATA.setFPEmbedVersion("1.0.0");
            ENVIRONMENT_DATA.setFPEmbed("core-sdk");
            aBoolean = true;
        }
        PlayerEngine playerEngine = new PlayerEngine(customOptions);
        playerEngine.addListener(new CoreObserverA(tag));
        CONCURRENT_HASH_MAP.put(tag, playerEngine);
        return playerEngine;
    }

    /**
     * Sets the client event listener to handle events.
     *
     * @param eventObserver The event listener that will handle events in the Core class.
     */
    public static void setClientEventListener(EventObserver eventObserver) {
        Hub.eventObserver = eventObserver;
    }

    /**
     * Destroys the CorePlayer instance associated with the given tag by removing it from the map
     * and calling its flush method to release resources.
     *
     * @param tag The unique identifier for the player instance to be destroyed.
     * @throws JSONException If there is an error handling JSON data.
     */
    public static void destroyPlayer(String tag) throws JSONException {
        PlayerEngine playerEngine;
        if ((playerEngine = CONCURRENT_HASH_MAP.remove(tag)) != null) {
            playerEngine.flush();
        }
    }

    /**
     * Dispatches an event to the CorePlayer instance associated with the given tag.
     * If the player exists, it updates the session environment data before dispatching the event.
     *
     * @param tag    The unique identifier for the player instance.
     * @param eventContract The event to be dispatched to the player.
     * @throws JSONException If there is an error handling JSON data.
     */
    public static void dispatchEventForPlayer(String tag, EventContract eventContract) throws JSONException {
        PlayerEngine playerEngine;
        if ((playerEngine = CONCURRENT_HASH_MAP.get(tag)) != null) {
            STREAMING_SESSION.updateEnvironmentData(ENVIRONMENT_DATA);
            playerEngine.dispatch(eventContract);
        }
    }

    /**
     * Handles an orientation change for the specified CorePlayer instance.
     * If the player exists, it dispatches the orientation change event.
     *
     * @param playerValue          The unique identifier for the player instance.
     * @param playerViewOrientation The new orientation to be set for the player.
     * @throws JSONException If there is an error handling JSON data.
     */
    public static void orientationChangeForPlayer(String playerValue, PlayerViewOrientation playerViewOrientation) throws JSONException {
        PlayerEngine playerEngine;
        if ((playerEngine = CONCURRENT_HASH_MAP.get(playerValue)) != null) {
            playerEngine.dispatchOrientationChange(playerViewOrientation);
        }
    }

    /**
     * Updates the environment and viewer data based on the provided DataEvent.
     * This ensures that the latest data from the event is reflected in the global environment and viewer context.
     *
     * @param streamingData The event containing updated environment and viewer data.
     * @throws JSONException If there is an error handling JSON data.
     */
    public static void dispatchDataEvent(StreamingData streamingData) throws JSONException {
        ENVIRONMENT_DATA.update(streamingData.getEnvironmentData());
        VIEWER_DATA.update(streamingData.getViewerData());
    }

    /**
     * Enables or disables Logcat output for a specific CorePlayer instance.
     * If the player exists, it updates its logging configuration.
     *
     * @param mapValue The unique identifier for the player instance.
     * @param logcat   Indicates whether Logcat output should be enabled or disabled.
     * @param isTrue   Additional flag to control logging behavior (purpose depends on implementation).
     */
    public static void allowLogcatOutputForPlayer(String mapValue, boolean logcat, boolean isTrue) {
        PlayerEngine playerEngine;
        if ((playerEngine = CONCURRENT_HASH_MAP.get(mapValue)) != null) {
            playerEngine.allowLogcatOutput(logcat, isTrue);
        }
    }

    /**
     * CoreListenerA is an event listener that handles events for a specific player.
     * It ensures that trackable events are associated with environment and viewer data.
     */
    static class CoreObserverA extends AbstractEventObserver {
        private final String listener;

        public CoreObserverA(String stListener) {
            this.listener = stListener;
        }

        public final void handle(EventContract eventContract) throws JSONException {
            if (eventContract.isTrackable()) {
                MonitoredEventContract monitoredEvent = (MonitoredEventContract) eventContract;
                monitoredEvent.setEnvironmentData(Hub.ENVIRONMENT_DATA);
                monitoredEvent.setViewerData(Hub.VIEWER_DATA);
                if (Hub.eventObserver != null) {
                    Hub.eventObserver.handle(eventContract);
                    return;
                }
                PlayerEngine playerEngine;
                if ((playerEngine = Hub.CONCURRENT_HASH_MAP.get(this.listener)) != null) {
                    playerEngine.handle(eventContract);
                    return;
                }
            }
        }

        public final void flush() throws JSONException {
            PlayerEngine playerEngine;
            if ((playerEngine = Hub.CONCURRENT_HASH_MAP.get(this.listener)) != null) {
                playerEngine.flush();
            }
        }
    }
}
