package io.fastpix.data

import android.content.Context
import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.domain.enums.PlayerEventType
import io.fastpix.data.domain.model.events.BufferedEventBuilder
import io.fastpix.data.domain.model.events.BufferingEventBuilder
import io.fastpix.data.domain.model.events.EndedEventBuilder
import io.fastpix.data.domain.model.events.ErrorEventBuilder
import io.fastpix.data.domain.model.events.PauseEventBuilder
import io.fastpix.data.domain.model.events.PlayEventBuilder
import io.fastpix.data.domain.model.events.PlayerReadyEventBuilder
import io.fastpix.data.domain.model.events.PlayingEventBuilder
import io.fastpix.data.domain.model.events.PulseEventBuilder
import io.fastpix.data.domain.model.events.RequestCancelledEventBuilder
import io.fastpix.data.domain.model.events.RequestCompletedEventBuilder
import io.fastpix.data.domain.model.events.RequestFailedEventBuilder
import io.fastpix.data.domain.model.events.SeekedEventBuilder
import io.fastpix.data.domain.model.events.SeekingEventBuilder
import io.fastpix.data.domain.model.events.VariantChangedEventBuilder
import io.fastpix.data.domain.model.events.ViewBeginEventBuilder
import io.fastpix.data.domain.model.events.ViewCompletedEventBuilder
import io.fastpix.data.domain.repository.EventDispatcher
import io.fastpix.data.domain.state.SDKStateService
import io.fastpix.data.domain.state.SessionService
import io.fastpix.data.domain.wallclock.ViewWatchCounter
import io.fastpix.data.sdkBuild.SDKBuildConfig
import io.fastpix.data.utils.Logger
import io.fastpix.data.utils.ScalingTracker
import java.util.UUID

/**
 * FastPix Data SDK - Main entry point for the SDK
 */
val scalingTracker = ScalingTracker()

class FastPixDataSDK {

    private var isInitialized = false
    private var configuration: SDKConfiguration? = null
    private var context: Context? = null

    private var eventDispatcher: EventDispatcher? = null
    private var sdkStateService: SDKStateService? = null
    private var pendingReleaseToken: UUID? = null


    /**
     * Initialize the FastPix SDK with the provided configuration
     *
     * @param config SDK configuration containing all required parameters
     * @param context Android application context
     * @throws IllegalStateException if SDK is already initialized
     * @throws IllegalArgumentException if configuration is invalid
     */
    @Synchronized
    fun initialize(config: SDKConfiguration, context: Context) {
        if (isInitialized) {
            throw IllegalStateException("FastPix SDK is already initialized")
        }
        pendingReleaseToken = null
        // Store context
        this.context = context
        // Store configuration
        this.configuration = config
        if (config.beaconUrl?.isNotEmpty() == true) {
            SDKBuildConfig.SDK_URL = "https://${config.workspaceId}.${config.beaconUrl}"
        } else {
            SDKBuildConfig.SDK_URL = "https://${config.workspaceId}.metrix.ws"
        }
        // Initialize dependency container
        DependencyContainer.initialize(context)
        ViewWatchCounter.reset()
        // Initialize dependencies
        initializeDependencies()

        // Initialize logging if enabled
        if (config.enableLogging) {
            Logger.enableLogging()
        }
        // Mark as initialized
        ViewWatchCounter.start()
        isInitialized = true
    }

    private fun initializeDependencies() {
        if (DependencyContainer.getViewerPref()?.getViewerId() == null) {
            DependencyContainer.getViewerPref()?.viewerId(UUID.randomUUID().toString())
        }
        DependencyContainer.getViewerPref()?.saveSdkUrl(SDKBuildConfig.SDK_URL)
        SessionService.initializeSession()
        eventDispatcher = DependencyContainer.getEventDispatcher()
        sdkStateService = DependencyContainer.getSDKStateService()
        configuration?.let { config ->
            sdkStateService?.updateSDKConfiguration(config)
        }
    }

    /**
     * Dispatch a player event
     *
     * @param event The player event to dispatch
     * @throws IllegalStateException if SDK is not initialized
     */
    fun dispatchEvent(event: PlayerEventType, playheadTimeOverride: Int? = null) {
        if (!isInitialized) return
        val config = configuration ?: return
        if (SessionService.validateSession()) {
            when (event) {
                PlayerEventType.play -> {
                    ViewWatchCounter.start()
                    val playEvent = PlayEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(playEvent)
                }

                PlayerEventType.viewBegin -> {
                    ViewWatchCounter.start()
                    val viewBeginEvent = ViewBeginEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(viewBeginEvent)
                }

                PlayerEventType.playerReady -> {
                    ViewWatchCounter.start()
                    val playerReadyEvent = PlayerReadyEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(playerReadyEvent)
                }

                PlayerEventType.seeked -> {
                    val seekedEvent = SeekedEventBuilder.build(config, playheadTimeOverride)
                    eventDispatcher?.dispatchEvent(seekedEvent)
                }

                PlayerEventType.variantChanged -> {
                    val variantChangeEvent = VariantChangedEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(variantChangeEvent)
                }

                PlayerEventType.playing -> {
                    ViewWatchCounter.start()
                    val playingEvent = PlayingEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(playingEvent)
                }

                PlayerEventType.seeking -> {
                    val seekingEvent = if (playheadTimeOverride != null) {
                        SeekingEventBuilder.build(config, playheadTimeOverride)
                    } else {
                        SeekingEventBuilder.build(config)
                    }
                    eventDispatcher?.dispatchEvent(seekingEvent)
                }

                PlayerEventType.pause -> {
                    ViewWatchCounter.pause()
                    val pauseEvent = if (playheadTimeOverride != null) {
                        PauseEventBuilder.build(config, playheadTimeOverride)
                    } else {
                        PauseEventBuilder.build(config)
                    }
                    eventDispatcher?.dispatchEvent(pauseEvent)
                }

                PlayerEventType.buffering -> {
                    ViewWatchCounter.start()
                    val bufferingEvent = BufferingEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(bufferingEvent)
                }

                PlayerEventType.buffered -> {
                    val bufferedEvent = BufferedEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(bufferedEvent)
                }

                PlayerEventType.ended -> {
                    ViewWatchCounter.pause()
                    val endedEvent = EndedEventBuilder.build(config, playheadTimeOverride)
                    eventDispatcher?.dispatchEvent(endedEvent)
                }

                PlayerEventType.viewCompleted -> {
                    val viewCompletedEvent = ViewCompletedEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(viewCompletedEvent)
                }

                PlayerEventType.error -> {
                    val errorEvent = ErrorEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(errorEvent)
                }

                PlayerEventType.requestCanceled -> {
                    val requestCancelled = RequestCancelledEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(requestCancelled)
                }

                PlayerEventType.requestFailed -> {
                    val requestFailed = RequestFailedEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(requestFailed)
                }

                PlayerEventType.requestCompleted -> {
                    val requestCompleted = RequestCompletedEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(requestCompleted)
                }

                PlayerEventType.pulse -> {
                    val pulse = PulseEventBuilder.build(config)
                    eventDispatcher?.dispatchEvent(pulse)
                }
            }
        } else {
            SessionService.initializeSession()
            sdkStateService?.viewBeginCalled()
            val playerReadyEvent = PlayerReadyEventBuilder.build(config)
            eventDispatcher?.dispatchEvent(playerReadyEvent)
            val viewBeginEvent = ViewBeginEventBuilder.build(config)
            eventDispatcher?.dispatchEvent(viewBeginEvent)
        }

    }

    /**
     * Reset the SDK (for testing purposes)
     */
    fun release(playheadTimeOverride: Int? = null) {
        playheadTimeOverride?.let {
            releaseInternal(playheadTimeOverride)
        } ?: run {
            releaseInternal()
        }
    }

    @Synchronized
    private fun releaseInternal(playheadTimeOverride: Int? = null) {
        if (!isInitialized && configuration == null && pendingReleaseToken == null) {
            Logger.logWarning("FastPixDataSDK", "release() called before initialization")
            return
        }

        if (pendingReleaseToken != null) {
            Logger.logWarning("FastPixDataSDK", "release() already in progress")
            return
        }
        configuration?.let {
            val viewCompletedEvent =
                ViewCompletedEventBuilder.build(it, playheadTimeOverride)
            DependencyContainer.getEventPersistenceManager()
                .savePendingEvents(listOf(viewCompletedEvent))
        }
        val releaseToken = UUID.randomUUID()
        pendingReleaseToken = releaseToken
        DependencyContainer.prepareForRelease()
        eventDispatcher?.cleanThroughWorkManager {
            onReleaseCleanupComplete(releaseToken)
        } ?: onReleaseCleanupComplete(releaseToken)
    }

    private fun onReleaseCleanupComplete(token: UUID) {
        synchronized(this) {
            if (pendingReleaseToken != token) {
                return
            }

            pendingReleaseToken = null

            if (!isInitialized) {
                context = null
                Logger.log("FastPixDataSDK", "Release cleanup completed")
            }
        }
    }
}