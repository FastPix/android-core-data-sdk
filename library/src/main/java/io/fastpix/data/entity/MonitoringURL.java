package io.fastpix.data.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MonitoringURL {
    public String protocol;
    public String host;
    public final Map<String, String> query = new ConcurrentHashMap<>();

    public MonitoringURL() {
    }
}
