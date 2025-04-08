package io.fastpix.data.Interfaces;

import org.json.JSONException;

public interface EventEmitter {
    void dispatch(EventContract eventContract) throws JSONException;
}
