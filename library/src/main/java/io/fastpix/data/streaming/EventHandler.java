package io.fastpix.data.streaming;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.Interfaces.EventObserver;

public class EventHandler implements EventEmitter {
    private int eventId = 0;
    private ConcurrentHashMap<Integer, EventObserver> concurrentHashMap = new ConcurrentHashMap<>();
    private HashSet<Integer> hashSet = new HashSet<>();

    public EventHandler() {
    }

    /**
     * Adds an event listener to the concurrent hash map and assigns it a unique ID.
     * The listener's ID is set using the current eventId, which is then incremented.
     * @param eventObserver The event listener to be added.
     */
    public void addListener(EventObserver eventObserver) {
        eventObserver.setId(this.eventId);
        this.concurrentHashMap.put(this.eventId++, eventObserver);
    }

    /**
     * Adds an event listener only once by storing the current eventId in a HashSet.
     * However, this implementation does not check if the eventId already exists,
     * meaning duplicate additions may still occur.
     * @param eventObserver The event listener to be added.
     */
    public void addListenerOnce(EventObserver eventObserver) {
        this.hashSet.add(this.eventId);
        this.addListener(eventObserver);
    }

    /**
     * Removes an event listener from the concurrent hash map using its assigned ID.
     * @param eventObserver The event listener to be removed.
     */
    public void removeListener(EventObserver eventObserver) {
        this.concurrentHashMap.remove(eventObserver.getId());
    }

    /**
     * Removes all event listeners by resetting the concurrent hash map and hash set.
     * This effectively clears all stored listeners and their associated IDs.
     */
    public void removeAllListeners() {
        this.concurrentHashMap = new ConcurrentHashMap<>();
        this.hashSet = new HashSet<>();
    }

    /**
     * Dispatches an event to all registered event listeners.
     * Iterates through the stored listeners and invokes their `handle` method.
     * If a listener's ID is found in the hashSet (indicating a one-time listener),
     * it is scheduled for removal after handling the event.
     * @param eventContract The event to be dispatched to all listeners.
     * @throws JSONException If an error occurs while processing the event.
     */
    public void dispatch(EventContract eventContract) throws JSONException {
        ArrayList arrayList = new ArrayList();
        int iEventListenerId;

        for (int i = 0; i < this.eventId; ++i) {
            EventObserver eventObserver;
            if ((eventObserver = this.concurrentHashMap.get(i)) != null) {
                eventObserver.handle(eventContract);
                iEventListenerId = eventObserver.getId();
                if (this.hashSet.contains(iEventListenerId)) {
                    arrayList.add(iEventListenerId);
                }
            }
        }

        Iterator iterator = arrayList.iterator();

        while (iterator.hasNext()) {
            iEventListenerId = (Integer) iterator.next();
            this.concurrentHashMap.remove(iEventListenerId);
        }
    }

    /**
     * Calls the `flush` method on all registered event listeners.
     * Iterates through the stored listeners and invokes their `flush` method,
     * allowing them to perform any necessary cleanup or state resetting.
     * @throws JSONException If an error occurs during the flush operation.
     */
    public void flush() throws JSONException {
        for (int i = 0; i < this.eventId; ++i) {
            EventObserver eventObserver;
            if ((eventObserver = this.concurrentHashMap.get(i)) != null) {
                eventObserver.flush();
            }
        }
    }
}

