package io.fastpix.data.entity;

public class UserSessionTag {
    public final String key;
    public final String value;

    public UserSessionTag(String sessionKey, String sessionValue) {
        this.key = sessionKey;
        this.value = sessionValue;
    }

    public String toString() {
        return "{ key='" + this.key + "', value='" + this.value + "'}";
    }
}
