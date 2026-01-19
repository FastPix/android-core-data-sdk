package io.fastpix.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.sdkBuild.SDKBuildConfig
import io.fastpix.data.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * WorkManager Worker that handles cleanup of EventDispatcher
 * This ensures cleanup happens even if the app is terminated
 */
class CleanupWorker(
    context: Context, params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            delay(2000)
            Logger.log("CleanupWorker", "Starting cleanup work")

            // Ensure DependencyContainer is initialized with application context
            try {
                DependencyContainer.getContext()
            } catch (e: IllegalStateException) {
                // If not initialized, initialize it with the application context
                DependencyContainer.initialize(applicationContext)
            }

            // Restore SDK_URL from SharedPreferences if it's empty (process restart scenario)
            // This is needed because static variables don't persist across process boundaries
            if (SDKBuildConfig.SDK_URL.isBlank()) {
                val savedSdkUrl = DependencyContainer.getViewerPref()?.getSdkUrl()
                if (!savedSdkUrl.isNullOrBlank()) {
                    SDKBuildConfig.SDK_URL = savedSdkUrl
                    Logger.log(
                        "CleanupWorker", "Restored SDK_URL from SharedPreferences: $savedSdkUrl"
                    )
                } else {
                    Logger.logWarning(
                        "CleanupWorker",
                        "SDK_URL not found in SharedPreferences, cannot proceed with cleanup"
                    )
                    return Result.success() // Return success since there's nothing to cleanup
                }
            }

            val eventDispatcher = DependencyContainer.getEventDispatcher()
            val persistenceManager = DependencyContainer.getEventPersistenceManager()

            // Load persisted events that were saved before app termination
            val persistedEvents = persistenceManager.loadPendingEvents()

            // Perform cleanup synchronously
            withContext(Dispatchers.IO) {
                if (persistedEvents.isNotEmpty()) {
                    Logger.log(
                        "CleanupWorker", "Found ${persistedEvents.size} persisted events to send"
                    )

                    val batchedByVeid = persistedEvents.groupBy(
                        keySelector = { it.viewId },
                        valueTransform = { it })
                    batchedByVeid.values.map { events ->
                        // Send persisted events
                        // Note: sendPersistedEvents() now deletes events from persistence BEFORE sending
                        // to prevent race conditions and duplicates
                        val (allSuccess, successfullySentEvents) = eventDispatcher.sendPersistedEvents(
                            events
                        )

                        if (allSuccess) {
                            Logger.log(
                                "CleanupWorker",
                                "Successfully sent all ${events.size} persisted events"
                            )
                            // Note: Events are already deleted by sendPersistedEvents() before sending
                            // No need to delete again here
                        } else {
                            Logger.logWarning(
                                "CleanupWorker",
                                "Failed to send some persisted events, remaining events kept for retry"
                            )
                            // Note: Failed events are already added back to persistence by sendPersistedEvents()
                        }
                        DependencyContainer.getEventPersistenceManager().clearPendingEvents()
                    }
                } else {
                    Logger.log("CleanupWorker", "No persisted events found to send")
                }

                Logger.log("CleanupWorker", "Cleanup work completed successfully")
            }
            Result.success()
        } catch (e: Exception) {
            Logger.logError("CleanupWorker", "Error during cleanup work", e)
            DependencyContainer.getEventPersistenceManager().clearPendingEvents()
            // Don't retry on failure - cleanup is best effort
            // Retrying might cause issues if the app state is invalid
            // Don't clear events on error - they should be retried later
            Result.failure()
        }
    }
}

