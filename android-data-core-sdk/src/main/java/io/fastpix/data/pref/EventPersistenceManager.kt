package io.fastpix.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.fastpix.data.utils.Logger

/**
 * Manages persistent storage of unsent events
 * Similar to Mux Data SDK's approach of saving events for replay
 */
class EventPersistenceManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("fastpix_event_storage", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_PENDING_EVENTS = "pending_events"
        private const val KEY_EVENT_TIMESTAMP = "event_timestamp"
        private const val MAX_STORED_EVENTS = 500 // Prevent unbounded growth
        private const val MAX_EVENT_AGE_MS = 7 * 24 * 60 * 60 * 1000L // 7 days
    }

    /**
     * Save events to persistent storage
     * Called when app is about to terminate and events haven't been sent
     * 
     * @param synchronous If true, uses commit() for synchronous write (critical for app termination).
     *                    If false, uses apply() for async write (normal operation).
     */
    fun savePendingEvents(events: List<Map<String, String?>>, synchronous: Boolean = false) {
        if (events.isEmpty()) return

        try {
            val existingEvents = loadPendingEvents().toMutableList()
            
            // Add new events
            existingEvents.addAll(events)
            
            // Limit total events to prevent unbounded growth
            val eventsToSave = if (existingEvents.size > MAX_STORED_EVENTS) {
                existingEvents.takeLast(MAX_STORED_EVENTS)
            } else {
                existingEvents
            }
            
            // Convert to JSON and save
            val jsonString = gson.toJson(eventsToSave)
            
            if (synchronous) {
                // Use commit() for synchronous write - blocks until write completes
                // Critical for app termination scenarios
                prefs.edit().apply {
                    putString(KEY_PENDING_EVENTS, jsonString)
                    putLong(KEY_EVENT_TIMESTAMP, System.currentTimeMillis())
                    commit() // Synchronous - ensures data is written before process can be killed
                }
                Logger.log("EventPersistenceManager", "Saved ${eventsToSave.size} events to persistent storage (synchronous)")
            } else {
                // Use apply() for async write - normal operation
                prefs.edit {
                    putString(KEY_PENDING_EVENTS, jsonString)
                    putLong(KEY_EVENT_TIMESTAMP, System.currentTimeMillis())
                }
                Logger.log("EventPersistenceManager", "Saved ${eventsToSave.size} events to persistent storage")
            }
        } catch (e: Exception) {
            Logger.logError("EventPersistenceManager", "Failed to save pending events", e)
        }
    }

    /**
     * Load pending events from persistent storage
     * Called on app startup to replay unsent events
     */
    fun loadPendingEvents(): List<Map<String, String?>> {
        return try {
            val jsonString = prefs.getString(KEY_PENDING_EVENTS, null)
            if (jsonString.isNullOrEmpty()) {
                emptyList()
            } else {
                val type = object : TypeToken<List<Map<String, String?>>>() {}.type
                val events = gson.fromJson<List<Map<String, String?>>>(jsonString, type)
                
                // Filter out old events (older than MAX_EVENT_AGE_MS)
                val timestamp = prefs.getLong(KEY_EVENT_TIMESTAMP, 0)
                val currentTime = System.currentTimeMillis()
                
                if (currentTime - timestamp > MAX_EVENT_AGE_MS) {
                    Logger.log("EventPersistenceManager", "Events are too old, clearing storage")
                    clearPendingEvents()
                    emptyList()
                } else {
                    events ?: emptyList()
                }
            }
        } catch (e: Exception) {
            Logger.logError("EventPersistenceManager", "Failed to load pending events", e)
            emptyList()
        }
    }

    /**
     * Clear all pending events from storage
     * Called after successfully sending events
     */
    fun clearPendingEvents() {
        prefs.edit {
            clear()
        }
        Logger.log("EventPersistenceManager", "Cleared pending events from storage")
    }

    /**
     * Check if there are pending events
     */
    fun hasPendingEvents(): Boolean {
        return prefs.getString(KEY_PENDING_EVENTS, null) != null
    }
}

