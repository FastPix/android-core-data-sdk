package io.fastpix.data.monitor;

import android.util.Log;
import io.fastpix.data.request.CustomOptions;
import io.fastpix.data.streaming.AbstractEventObserver;
import io.fastpix.data.streaming.OveruseDetectedEventContract;
import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.streaming.MonitoredEventContract;
import io.fastpix.data.entity.QueryDataEntity;
import io.fastpix.data.entity.BasicQueryData;
import io.fastpix.data.request.AnalyticsEventLogger;
import io.fastpix.data.request.FastPixMetrics;
import io.fastpix.data.Interfaces.RequestHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * BeaconBatchTracker is a subclass of BaseEventListener that handles the tracking of beacon events
 * and manages the completion of network requests associated with those events. It implements the
 * IFPNetworkRequestsCompletion2 interface to handle the results of network requests asynchronously.
 *
 * This class is responsible for batch processing beacon-related events, sending those events to
 * a network endpoint, and handling the network request completion and responses.
 */
public class SignalBatchMonitor extends AbstractEventObserver implements RequestHandler.IFPNetworkRequestsCompletion2 {
    /**
     * The event name used for tracking events.
     */
    public static final String EVENT_NAME = "evna";

    /**
     * The beacon domain associated with the events.
     */
    public static final String BEACON_DOMAIN = "bedn";

    /**
     * The constant for identifying when a view has been completed.
     */
    public static final String VIEW_COMPLETED = "viewCompleted";

    /**
     * The name of the event queue used for FP stats events.
     */
    public static final String FP_STATS_EVENT_QUEUE = "FPStatsEventQueue";

    /**
     * The elapsed time for the last completed round trip in milliseconds.
     */
    protected long roundTripLastCompletedTimeElapsed;

    /**
     * The start time of the current round trip in milliseconds.
     */
    protected long roundTripStartTime;

    /**
     * Flag indicating whether the last round trip succeeded.
     */
    protected boolean roundTripLastSucceded;

    /**
     * The timestamp of the last beacon sent, in milliseconds.
     * Defaults to 0L indicating no beacon has been sent.
     */
    protected long lastBeaconSentTime = 0L;

    /**
     * The number of failures encountered during beacon sending.
     */
    protected int failureCount = 0;

    /**
     * A boolean flag used for internal state management.
     */
    private boolean aBoolean = true;

    String valueOfUnUsed = "";

    /**
     * Another boolean flag used for internal state management.
     */
    private boolean isVerbose = false;

    /**
     * An instance of TrackerCoreA for core tracking functionality.
     */
    private TrackerEngineMonitor trackerEngineMonitor;

    /**
     * A list of events that are queued for tracking and dispatching.
     */
    protected ArrayList<MonitoredEventContract> eventQueue = new ArrayList();

    /**
     * A list of events that are pending and waiting for completion before being dispatched.
     */
    protected ArrayList<MonitoredEventContract> pendingEventsQueue = new ArrayList();

    /**
     * The network request dispatcher for sending events, typically connected to the API used for tracking.
     */
    protected RequestHandler dispatcher = FastPixMetrics.getHostNetworkApi();

    /**
     * The workspace ID used for organizing the tracking context.
     */
    private String workSpaceId = null;

    /**
     * The scheduled executor service for managing periodic tasks.
     */
    private ScheduledExecutorService scheduledExecutorService;

    /**
     * A set of event types that are commonly tracked by this class.
     */
    private final Set<String> hashSet = new HashSet(Arrays.asList("viewBegin", "error", "ended", VIEW_COMPLETED));

    /**
     * Flag indicating whether the current event is related to a trackball event.
     */
    private boolean isTrackballEvent = false;

    /**
     * A custom options object that holds additional configurations for the tracker.
     */
    private final CustomOptions customOptions;

    /**
     * A long variable used for some internal tracking purposes.
     */
    private long aLong = 0L;

    /**
     * A base query data object that holds the common tracking data for events.
     */
    private QueryDataEntity queryDataEntity = null;

    /**
     * A set of additional keys used for tracking certain parameters in the event data.
     */
    private final Set<String> hashSet1 = new HashSet(Arrays.asList("wsid", "veid", "vesqnu", "plsqnu", "plphti", "plinid", "vitp", "fpaivn", "vdid"));


    /**
     * Constructor for initializing the BeaconBatchTracker with custom options.
     *
     * The constructor configures the tracker based on the provided custom options,
     * including setting the appropriate core tracker and scheduling the periodic task
     * for dispatching events.
     *
     * @param customOptions The custom options that control the behavior of the tracker.
     */
    public SignalBatchMonitor(CustomOptions customOptions) {
        this.customOptions = customOptions;
        if (this.customOptions.isLongBeaconDispatch()) {
            this.trackerEngineMonitor = TrackerEngineMonitor.TRACKER_CORE_A1;
        } else {
            this.trackerEngineMonitor = TrackerEngineMonitor.TRACKER_CORE_A;
        }

        this.scheduledExecutorService = Executors.newScheduledThreadPool(2);

        ScheduledFuture<?> runtimeException = this.scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                this.aTrack(null);
            } catch (JSONException e) {
                Log.e("RuntimeException", e.toString());
            }
        }, 0L, 1000L, TimeUnit.MILLISECONDS);

        valueOfUnUsed = runtimeException.toString();
    }


    /**
     * Releases resources used by the BeaconBatchTracker, specifically the scheduled executor service.
     *
     * This method shuts down the scheduled executor service, preventing any further scheduled tasks
     * from executing, and sets the executor service reference to null to release the resource.
     */
    public void release() {
        if (this.scheduledExecutorService != null) {
            this.scheduledExecutorService.shutdown();
            this.scheduledExecutorService = null;
        }
    }

    public void setVerboseMode(boolean verboseMode) {
        this.isVerbose = verboseMode;
    }

    /**
     * Handles the incoming event by processing it according to its type.
     *
     * This method processes an event, typically involving updating state or triggering necessary actions
     * based on the type of the event. It may throw a JSONException if the event's data is improperly formatted.
     *
     * @param eventContract The event to be processed.
     * @throws JSONException If there is an issue parsing or handling the event's data.
     */
    public void handle(EventContract eventContract) throws JSONException {
        MonitoredEventContract monitoredEvent = (MonitoredEventContract) eventContract;

        if (this.isTrackballEvent) {
            AnalyticsEventLogger.d(FP_STATS_EVENT_QUEUE, String.format(
                    "Event not handled, rate-limited: %b, queue size: %d, queue limit: 3600",
                    this.isTrackballEvent, this.eventQueue.size()
            ));
            return;
        }

        String eventType = monitoredEvent.getEventType();
        QueryDataEntity queryData = monitoredEvent.getQuery();
        callToViewBeginAndViewCompeted(eventType, queryData);

        this.aLong = System.currentTimeMillis();
        this.isTrackballEvent = !this.aTrack(monitoredEvent);

        if (this.hashSet.contains(eventType) || this.isTrackballEvent) {
            if (this.isTrackballEvent) {
                this.eventQueue.add(new OveruseDetectedEventContract(monitoredEvent));
            }
            this.flush();
        }
    }

    /**
     * Handles the processing of events for "viewBegin" and "viewCompleted".
     * If the event type is neither of these and the time difference from
     * the last event is less than 10 minutes, it processes the data in the event and
     * updates the query data by merging it with the current `baseQueryData`.
     * It iterates over the keys in the event's JSON object and appropriately updates
     * the `updatedQueryData` based on the data type (JSON object, JSON array, or simple value).
     * If the event type is "viewCompleted", it clears the `baseQueryData` to prepare for the next event.
     *
     * @param eventType The type of event (e.g., "viewBegin", "viewCompleted").
     * @param queryData The event data containing necessary details to update or merge into the `baseQueryData`.
     * @throws JSONException If there's an issue accessing or processing the event data in JSON format.
     */

    private void callToViewBeginAndViewCompeted(String eventType, QueryDataEntity queryData) throws JSONException {
        if (!eventType.equals("viewBegin") && !eventType.equals(VIEW_COMPLETED) &&
                this.queryDataEntity != null && System.currentTimeMillis() - this.aLong < 600000L) {

            JSONObject eventJson = queryData.getFPDictionary();
            BasicQueryData updatedQueryData = new BasicQueryData();

            Iterator<String> keys = eventJson.keys();
            while (keys.hasNext()) {
                String key = keys.next();

                if (QueryDataEntity.isKeyJsonObject(key)) {
                    updatedQueryData.put(key, eventJson.getJSONObject(key));
                } else if (QueryDataEntity.isKeyJsonArray(key)) {
                    updatedQueryData.put(key, eventJson.getJSONArray(key));
                } else {
                    String value = eventJson.getString(key);
                    callToElse(value, key, updatedQueryData);
                }
            }
            queryData.replace(updatedQueryData.getFPDictionary());
        } else {
            this.queryDataEntity = new BasicQueryData();
            this.queryDataEntity.update(queryData);

            if (eventType.equals(VIEW_COMPLETED)) {
                this.queryDataEntity = null;
            }
        }
    }

    /**
     * This method handles the conditional updating of query data by comparing values from the event data
     * with the current `baseQueryData`. It checks several conditions before adding the data to the `updatedQueryData`.
     *
     * The method does the following:
     * 1. It ensures that the value for the specified `key` either does not exist in the current `baseQueryData`,
     *    or that the value is different from the existing value in `baseQueryData`.
     * 2. It also checks whether the `key` is part of the predefined set `hashSet1`, or if the `key` is either
     *    "e" or starts with "q" or "d". If any of these conditions are true, it proceeds with updating.
     * 3. If the conditions are met, it adds the `key` and its corresponding `value` to both the `updatedQueryData`
     *    and `baseQueryData` for tracking and subsequent use.
     *
     * @param value The value associated with the key to be added to the data.
     * @param key The key associated with the value to be processed and updated in the data.
     * @param updatedQueryData The `SimpleBaseQueryData` object that gets updated with the key-value pair.
     * @throws JSONException If there's an issue accessing or processing the data in JSON format.
     */

    private void callToElse(String value, String key, BasicQueryData updatedQueryData) throws JSONException {
        if (this.queryDataEntity.get(key) == null ||
                !value.equals(this.queryDataEntity.get(key)) ||
                this.hashSet1.contains(key) ||
                key.equalsIgnoreCase("e") ||
                key.startsWith("q") || key.startsWith("d")) {

            updatedQueryData.put(key, value);
            this.queryDataEntity.put(key, value);
        }
    }

    /**
     * This method calculates the next time interval for sending a beacon based on the number of previous failures.
     * It uses an exponential backoff strategy for failure recovery, where the time interval increases with successive failures.
     *
     * If there have been no failures (i.e., `failureCount == 0`), the method simply returns the base tracking interval from `trackerCoreA`.
     * If failures have occurred, the method calculates the interval as follows:
     * - It first computes a random value between 1 and 2 raised to the power of `(failureCount - 1)`, which increases the interval exponentially.
     * - Then, it multiplies the random value by the base tracking interval (`trackerCoreA.trackIdInt`) and adds a random factor to it.
     *
     * The result is a longer interval between beacons as the failure count increases, helping to prevent sending repeated beacons too quickly after a failure.
     *
     * @return The calculated next time interval (in milliseconds) for sending the next beacon.
     */

    protected long getNextBeaconTimeInterval() {
        if (this.failureCount == 0) {
            return this.trackerEngineMonitor.trackIdInt;
        } else {
            double valRandom = Math.pow(2.0, (this.failureCount - 1)) * Math.random();
            return (long) ((valRandom + 1.0) * this.trackerEngineMonitor.trackIdInt);
        }
    }

    /**
     * This method handles the tracking of events by adding them to an event queue and processing them at a specified interval.
     *
     * The method first checks if the event queue has reached its maximum size (3600 events). If the queue has space and the `trackableEvent`
     * is not null, the event is added to the queue. After that, the method checks whether enough time has passed since the last beacon was sent.
     * If the specified time interval has passed, it processes the events in the queue and updates the timestamp for the last beacon sent.
     *
     * The method ensures that the event queue size never exceeds 3600. If the queue is full, the event is not added, and a rate-limiting message
     * is logged. The method returns `true` if the event was successfully added or the queue size is within the limit, and `false` if the event
     * was not added due to rate-limiting.
     *
     * @param monitoredEvent The event to be tracked. If not null, it is added to the queue.
     * @return A boolean indicating whether the event was successfully added to the queue and processed, or if the queue is full (rate-limited).
     */
    private synchronized boolean aTrack(MonitoredEventContract monitoredEvent) throws JSONException {
        if (this.eventQueue.size() < 3600) {
            if (monitoredEvent != null) {
                this.eventQueue.add(monitoredEvent);
            }

            if (System.currentTimeMillis() - this.lastBeaconSentTime > this.getNextBeaconTimeInterval()) {
                this.processEvents(false);
                this.lastBeaconSentTime = System.currentTimeMillis();
            }

            return this.eventQueue.size() <= 3600;
        } else {
            AnalyticsEventLogger.d(FP_STATS_EVENT_QUEUE, "Event not queued, ratelimited: " + this.isTrackballEvent + ",queue size: " + this.eventQueue.size() + ", queue limit: 3600");
            return false;
        }
    }

    /**
     * This method forces the immediate processing of all events in the event queue.
     * It calls the `processEvents` method with a `true` flag, which indicates that
     * the events should be processed immediately, bypassing any time-based or
     * rate-limiting checks that may normally apply.
     *
     * This method is typically used when you want to ensure that the events are sent
     * to the server without waiting for the next scheduled interval.
     *
     * @throws JSONException If there is an issue processing the events (e.g., invalid JSON data).
     */

    public void flush() throws JSONException {
        this.processEvents(true);
    }


    /**
     * This method processes the events in the event queue and attempts to send them to the server.
     * The method can either send a limited number of events (based on the tracker core's track code limit)
     * or force the sending of all events in the queue, depending on the value of the `forceSend` parameter.
     *
     * It retrieves the specified number of events from the queue, prepares them for sending by converting
     * the event data into a JSON format, and logs useful information regarding the sending process.
     * The method also ensures that the events are properly tagged with necessary metadata (like event types
     * and beacon domains) before being sent. Additionally, it updates the state of the event queues based on
     * the outcome.
     *
     * @param forceSend A boolean flag that, when true, forces the immediate sending of all events
     *                  regardless of rate-limiting constraints.
     *
     * @throws JSONException If there is an issue processing or constructing the event JSON data.
     */

    private void processEvents(boolean forceSend) throws JSONException {
        int eventCount = forceSend ? this.eventQueue.size() : Math.min(this.eventQueue.size(), this.trackerEngineMonitor.trackCodeInt);

        if (eventCount == 0) return;

        AnalyticsEventLogger.d(FP_STATS_EVENT_QUEUE, String.format("Attempting to send %d events, total queue size: %d", eventCount, this.eventQueue.size()));

        if ((this.aBoolean || forceSend) && this.dispatcher != null) {
            try {
                JSONArray eventArray = new JSONArray();
                StringBuilder eventTypes = new StringBuilder();
                String beaconDomain = "";

                for (int i = 0; i < eventCount && !this.eventQueue.isEmpty(); i++) {
                    MonitoredEventContract monitoredEvent = this.eventQueue.remove(0);
                    this.pendingEventsQueue.add(monitoredEvent);

                    String eventType = monitoredEvent.getEventType();
                    eventTypes.append(eventType).append(", ");
                    beaconDomain = checkBeaconDomain(this.customOptions);

                    JSONObject eventData = monitoredEvent.getQuery().getFPDictionary();
                    eventData.put(EVENT_NAME, eventType);
                    eventData.put(BEACON_DOMAIN, beaconDomain.substring(1));

                    JSONArray keys = eventData.names();

                    if (this.isVerbose) {
                        AnalyticsEventLogger.d(FP_STATS_EVENT_QUEUE, String.format("Sending %s%n%s", eventType, "trackableEvent"));
                    } else {
                        AnalyticsEventLogger.d(FP_STATS_EVENT_QUEUE, String.format("Sending %s with %d attributes", eventType, keys.length()));
                    }

                    for (int j = 0; j < keys.length(); j++) {
                        String key = keys.getString(j);
                        if (key.equals("wsid") && this.workSpaceId == null) {
                            this.workSpaceId = eventData.getString(key);
                        }
                    }

                    eventArray.put(eventData);
                }

                // Construct event metadata
                callToContractEventsMetaData(eventArray, forceSend, eventCount, eventTypes, beaconDomain);

            } catch (Exception e) {
                AnalyticsEventLogger.exception(e, FP_STATS_EVENT_QUEUE, "Error sending Beacon Queue");
                this.aBoolean = true;
            }
        }
    }

    /**
     * This method constructs and sends a batch of events to a designated server endpoint
     * by formatting the event data and metadata into a JSON object. The events are packaged
     * into an array and sent with additional metadata such as round-trip time, transmission
     * timestamp, and other necessary identifiers. The method interacts with the dispatcher
     * to send the payload to the server asynchronously.
     *
     * The events are sent either immediately (flushed) or queued for later submission,
     * depending on the value of the `forceSend` flag. The event types and the number of events
     * being processed are logged for debugging and monitoring purposes.
     *
     * @param eventArray   A JSON array containing the events to be sent.
     * @param forceSend    A boolean flag indicating whether to force the immediate sending of events
     *                     or to submit them for later processing.
     * @param eventCount   The number of events being sent in this batch.
     * @param eventTypes   A StringBuilder containing a list of event types for logging purposes.
     * @param beaconDomain The domain to which the events will be sent.
     */

    private void callToContractEventsMetaData(JSONArray eventArray, boolean forceSend, int eventCount, StringBuilder eventTypes, String beaconDomain) {
        try {
            JSONObject eventPayload = new JSONObject();

            JSONObject metadata = new JSONObject();
            if (this.roundTripLastSucceded) {
                metadata.put("rtt_ms", this.roundTripLastCompletedTimeElapsed);
            }

            metadata.put("transmission_timestamp", System.currentTimeMillis());

            eventPayload.put("events", eventArray);
            eventPayload.put("metadata", metadata);

            AnalyticsEventLogger.d(FP_STATS_EVENT_QUEUE, String.format("%s %d events to batch handler", forceSend ? "Flushing" : "Submitting", eventCount));
            AnalyticsEventLogger.d(FP_STATS_EVENT_QUEUE, "    [" + eventTypes + "]");

            this.aBoolean = false;
            this.roundTripStartTime = System.currentTimeMillis();

            this.dispatcher.postWithCompletion(
                    beaconDomain, this.workSpaceId, eventPayload.toString(),
                    aHashString(this.workSpaceId), this
            );
        } catch (JSONException e) {
            Log.e("Exception", e.toString());
        }

    }

    /**
     * This method creates and returns a new `HashMap` object. The method signature indicates that
     * it accepts a string parameter (`var0`), but currently the parameter is not used in the method body.
     * It simply initializes and returns an empty `HashMap` with `String` keys and `String` values.
     *
     * @param var0 A string parameter that is not used in the current implementation.
     * @return A new, empty `HashMap` with `String` keys and `String` values.
     */

    private static HashMap<String, String> aHashString(String var0) {
        return new HashMap();
    }

    /**
     * This method is called upon completion of the beacon transmission process, indicating whether the transmission was successful.
     * It handles the result of the beacon transmission and manages the event queue based on whether the transmission was successful or failed.
     *
     * - If the transmission is not complete (failed), the method attempts to requeue the pending events, unless the event queue is full.
     * - If the transmission is complete (successful), the method updates metadata such as the round-trip time and resets relevant flags.
     *
     * @param isComplete A boolean indicating whether the transmission was successful (true) or failed (false).
     * @param stringListMap A map of string keys to lists of strings containing additional metadata, such as flush time.
     */

    public void onComplete(boolean isComplete, Map<String, List<String>> stringListMap) {
        AnalyticsEventLogger.d(FP_STATS_EVENT_QUEUE, "last batch handler result " + isComplete);
        this.aBoolean = true;
        if (!isComplete) {
            if (this.eventQueue.size() + this.pendingEventsQueue.size() < 3600) {
                this.eventQueue.addAll(0, this.pendingEventsQueue);
                ++this.failureCount;
            } else {
                this.roundTripLastSucceded = false;
                this.failureCount = 0;
                AnalyticsEventLogger.d(FP_STATS_EVENT_QUEUE, "event queue too large, dropping events failed to send !!!");
            }
        } else {
            long timeMillis = System.currentTimeMillis();
            this.roundTripLastCompletedTimeElapsed = timeMillis - this.roundTripStartTime;
            this.roundTripLastSucceded = true;
            this.failureCount = 0;
            List list;
            if (stringListMap != null && (list = stringListMap.get("x-litix-io-beacon-flush-ms")) != null && !list.isEmpty()) {
                try {
                    this.trackerEngineMonitor = TrackerEngineMonitor.a(Integer.parseInt((String) list.get(0)));
                } catch (NumberFormatException numberFormatException) {
                    Log.e("Exception", numberFormatException.toString());

                }
            }
        }
        this.pendingEventsQueue.clear();
    }

    /**
     * Determines the appropriate beacon domain to use based on the provided custom options.
     * The method first checks if a specific beacon collection domain is set. If so, it returns that domain.
     * If not, it falls back to the beacon domain provided in the custom options.
     * If neither domain is available, it defaults to ".metrix.ws".
     *
     * @param customOptions The custom options containing beacon domain information.
     * @return The selected beacon domain.
     */

    private static String checkBeaconDomain(CustomOptions customOptions) {
        String beaconDomain = customOptions.getBeaconDomain();
        String beaconCollectionDomain = customOptions.getBeaconCollectionDomain();
        if (beaconCollectionDomain != null && !beaconCollectionDomain.isEmpty()) {
            return beaconCollectionDomain;
        } else {
            return beaconDomain != null && !beaconDomain.isEmpty() ? beaconDomain : ".metrix.ws";
        }
    }
}
