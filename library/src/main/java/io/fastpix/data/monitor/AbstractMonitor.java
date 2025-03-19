package io.fastpix.data.monitor;

import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.Interfaces.EventObserver;
import io.fastpix.data.streaming.StreamingHub;

import org.json.JSONException;

/**
 * The BaseTracker class serves as a base class for tracking events and managing state in the tracking system.
 * It implements the ITracker interface and provides a foundation for subclasses to handle specific types of tracking events.
 * This class contains common functionality, such as dispatching events and managing shared state across subclasses.
 *
 * It is intended to be extended by other tracker classes (such as BaseAdTracker) that specialize in tracking specific types of events
 * or actions (e.g., ad tracking).
 */
public class AbstractMonitor implements EventObserver {
    private int id;
    protected EventEmitter dispatcher;

    /**
     * Constructs a BaseTracker object with the specified event dispatcher.
     *
     * @param eventEmitter The event dispatcher responsible for handling and dispatching events.
     * This constructor initializes the event dispatcher that will be used by the tracker to send events
     * during the tracking process. It allows the tracker to work in conjunction with an event-driven system
     * to notify other parts of the application of relevant events.
     */
    public AbstractMonitor(EventEmitter eventEmitter) {
        this.dispatcher = eventEmitter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
    }

    public void handle(EventContract eventContract) throws JSONException {
        if (eventContract.isPlayback()) {
            this.handlePlaybackEvent((StreamingHub) eventContract);
        }

    }

    public void flush() {
    }

    protected void dispatch(EventContract eventContract) throws JSONException {
        this.dispatcher.dispatch(eventContract);
    }
}
