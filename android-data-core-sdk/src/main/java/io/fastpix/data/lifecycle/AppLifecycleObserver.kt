package io.fastpix.data.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import io.fastpix.data.domain.repository.EventDispatcher
import io.fastpix.data.utils.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

/**
 * Lifecycle observer that detects app termination and triggers final event flush
 * Similar to Mux Data SDK's approach of using ActivityLifecycleCallbacks
 */
class AppLifecycleObserver(
    private val eventDispatcher: EventDispatcher
) : Application.ActivityLifecycleCallbacks {

    private val activityCount = AtomicInteger(0)
    private val lifecycleScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // No-op
    }

    override fun onActivityStarted(activity: Activity) {
        activityCount.incrementAndGet()
    }

    override fun onActivityResumed(activity: Activity) {
        // No-op
    }

    override fun onActivityPaused(activity: Activity) {
        // No-op
    }

    override fun onActivityStopped(activity: Activity) {
        val count = activityCount.decrementAndGet()

        // When all activities are stopped, app is going to background or being killed
        if (count == 0) {
            Logger.log("AppLifecycleObserver", "All activities stopped, triggering final flush")
            triggerFinalFlush()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // No-op
    }

    override fun onActivityDestroyed(activity: Activity) {
        // No-op
    }

    /**
     * Trigger final flush when app is being terminated
     * This is the "final beacon" mechanism
     */
    private fun triggerFinalFlush() {
        lifecycleScope.launch {
            try {
                // Use fire-and-forget flush with short timeout
                eventDispatcher.cleanThroughWorkManager()
            } catch (e: Exception) {
                Logger.logError("AppLifecycleObserver", "Error during final flush", e)
            }
        }
    }
}

