package io.fastpix.data.request;

import androidx.annotation.Nullable;

import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.Interfaces.EventObserver;
import io.fastpix.data.entity.CustomerDataEntity;
import io.fastpix.data.entity.CustomerPlayerDataEntity;
import io.fastpix.data.Interfaces.DeviceContract;
import io.fastpix.data.streaming.SupportStreaming;

import org.json.JSONException;

import java.util.Locale;

public class AnalyticsEventLogger {
    private static String string;
    private static EventObserver eventObserver;
    private static boolean isLoggerTrue = false;

    public AnalyticsEventLogger() {
    }

    public static void enable(String string, EventObserver eventObserver) {
        AnalyticsEventLogger.string = string;
        AnalyticsEventLogger.eventObserver = eventObserver;
    }

    private static boolean aEmptyParam() {
        return string != null && eventObserver != null;
    }

    private static boolean bEmptyParam() {
        return aEmptyParam() && string.indexOf("all") >= 0;
    }

    private static boolean aParam(String isTrue) {
        return aEmptyParam() && string.indexOf(isTrue) >= 0;
    }

    public static void event(String isITEmpty) throws JSONException {
        if (bEmptyParam() || aParam("event")) {
//            iEventListener.handle(new DebugEvent("type=event " + isITEmpty));
            eventObserver.handle(new SupportStreaming(SupportStreaming.Type.debugEvent, isITEmpty));
        }
    }

    public static void printEvent(String eventName, EventContract eventContract) {
        if (!eventContract.getType().equalsIgnoreCase("internalHeartbeat") && !eventContract.getType().equalsIgnoreCase("timeUpdate")) {
            d(eventName, "Sending event: " + eventContract.getType());
        }
    }

    public static void exception(Throwable throwable, String string, String string1) throws JSONException {
        exception(throwable, string, string1, (CustomerPlayerDataEntity) null);
    }

    public static void exception(Throwable throwable, String var1, String var2, @Nullable CustomerDataEntity customerData) throws JSONException {
        exception(throwable, var1, var2, customerData != null ? customerData.getCustomerPlayerData() : null);
    }

    public static void exception(Throwable throwable, String message, String messageCode, @Nullable CustomerPlayerDataEntity customerPlayerData) throws JSONException {
        if (bEmptyParam() || aParam("exception")) {
            String formatedSting = customerPlayerData != null ? customerPlayerData.getWorkspaceKey() : null;
            formatedSting = String.format(Locale.US, "type=exception For env %s%n%s%n%s", formatedSting, messageCode, throwable);
            if (eventObserver != null) {
                eventObserver.handle(new SupportStreaming(SupportStreaming.Type.debugEvent, formatedSting));
            }
            DeviceContract hostDevice = FastPixMetrics.getHostDevice();
            if (Boolean.TRUE.equals(isLoggerTrue) && hostDevice != null) {
                hostDevice.outputLog("error", message, messageCode, throwable);
            }
        }
    }

    public static void info(String info) throws JSONException {
        if (bEmptyParam() || aParam("info")) {
            eventObserver.handle(new SupportStreaming(SupportStreaming.Type.debugEvent, info));
        }
    }

    public static void d(String tag, String message) {
        DeviceContract hostDevice = FastPixMetrics.getHostDevice();
        if (Boolean.TRUE.equals(isLoggerTrue) && hostDevice != null) {
            hostDevice.outputLog("debug", tag, message);
        }
    }

    public static void w(String tag, String message) {
        DeviceContract hostDevice = FastPixMetrics.getHostDevice();
        if (Boolean.TRUE.equals(isLoggerTrue) && hostDevice != null) {
            hostDevice.outputLog("warn", tag, message);
        }
    }

    public static void setAllowLogcat(boolean allowLogcat) {
        isLoggerTrue = allowLogcat;
    }

    static {
        isLoggerTrue = Boolean.FALSE;
    }
}
