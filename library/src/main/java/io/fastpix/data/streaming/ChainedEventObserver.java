package io.fastpix.data.streaming;

import org.json.JSONException;

import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.Interfaces.EventEmitter;

/**
 * A custom event listener that redispatches events using an {@link EventEmitter}.
 * It extends {@link AbstractEventObserver} and provides functionality for redispatching events
 * unless the event is related to playback.
 */
public class ChainedEventObserver extends AbstractEventObserver {
    private final EventEmitter eventEmitter; // Dispatcher used to redispatch events

    /**
     * Constructs a {@code RedispatchEventListener} with a specific {@link EventEmitter}.
     * @param eventEmitter The dispatcher responsible for redispatching events.
     */
    public ChainedEventObserver(EventEmitter eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    /**
     * Handles the event by redispatching it unless the event is related to playback.
     * This method overrides the {@link AbstractEventObserver#handle(EventContract)} method.
     * @param eventContract The event to be handled and potentially redispatched.
     * @throws JSONException If an error occurs while processing the event.
     */
    @Override
    public void handle(EventContract eventContract) throws JSONException {
        // Only redispatch events that are not related to playback
        if (!eventContract.isPlayback()) {
            this.eventEmitter.dispatch(eventContract); // Redispatch the event
        }
    }
}
