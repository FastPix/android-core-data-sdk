package io.fastpix.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class ViewerPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("viewer_prefs", Context.MODE_PRIVATE)

    // Save a simple string (e.g., viewer name)
    fun viewerId(viewerName: String) {
        prefs.edit { putString("viewer_name", viewerName) }
    }

    // Get the stored string (viewer name)`
    fun getViewerId(): String? {
        return prefs.getString("viewer_name", null)
    }

    // Clear the stored string
    fun clearViewerName() {
        prefs.edit { remove("viewer_name") }
    }

    // Save SDK URL for persistence across process restarts
    fun saveSdkUrl(sdkUrl: String) {
        prefs.edit { putString("sdk_url", sdkUrl) }
    }

    // Get the stored SDK URL
    fun getSdkUrl(): String? {
        return prefs.getString("sdk_url", null)
    }

    // Clear the stored SDK URL
    fun clearSdkUrl() {
        prefs.edit { remove("sdk_url") }
    }
}
