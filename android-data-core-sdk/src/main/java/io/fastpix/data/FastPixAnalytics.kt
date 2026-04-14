package io.fastpix.data

import android.content.Context
import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.utils.Logger

/**
 * Simple public API for the FastPix Video Analytics SDK.
 * Lifecycle is managed internally. Use [FastPixBaseMedia3Player] (or equivalent) to attach a Media3 player;
 * that adapter will use this SDK instance when you call [initialize] first.
 *
 * Example:
 * - FastPixAnalytics.initialize(config, context)
 * - Create FastPixBaseMedia3Player(exoPlayer, ...) which uses the same config / SDK
 * - FastPixAnalytics.release() when done
 */
object FastPixAnalytics {
    private var sdk: FastPixDataSDK? = null

    @JvmStatic
    fun initialize(config: SDKConfiguration, context: Context) {
        if (sdk == null) {
            sdk = FastPixDataSDK()
        }
        Logger.configure(config.enableLogging)
        Logger.log("FastPixAnalytics", "ANALYTICS_INITIALIZE_CALLED")
        sdk?.initialize(config, context)
    }

    @JvmStatic
    fun release(playheadTimeOverride: Int? = null) {
        Logger.log("FastPixAnalytics", "ANALYTICS_RELEASE_CALLED")
        sdk?.release(playheadTimeOverride)
        sdk = null
    }

    /** Returns the underlying SDK instance for use by player adapters (e.g. FastPixBaseMedia3Player). */
    @JvmStatic
    fun getSDK(): FastPixDataSDK? = sdk
}
