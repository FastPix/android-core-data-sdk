package io.fastpix.data

import android.content.Context
import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.domain.enums.PlayerEventType
import io.fastpix.data.domain.listeners.PlayerListener
import io.fastpix.data.domain.model.events.BufferedEvent
import io.fastpix.data.domain.model.events.BufferingEvent
import io.fastpix.data.domain.model.events.EndedEvent
import io.fastpix.data.domain.model.events.ErrorEvent
import io.fastpix.data.domain.model.events.PauseEvent
import io.fastpix.data.domain.model.events.PlayEvent
import io.fastpix.data.domain.model.events.PlayerReadyEvent
import io.fastpix.data.domain.model.events.PlayingEvent
import io.fastpix.data.domain.model.events.RequestCancelledEvent
import io.fastpix.data.domain.model.events.RequestCompletedEvent
import io.fastpix.data.domain.model.events.RequestFailedEvent
import io.fastpix.data.domain.model.events.SeekedEvent
import io.fastpix.data.domain.model.events.SeekingEvent
import io.fastpix.data.domain.model.events.VariantChangedEvent
import io.fastpix.data.domain.model.events.ViewBeginEvent
import io.fastpix.data.domain.model.events.ViewCompletedEvent
import io.fastpix.data.domain.repository.EventDispatcher
import io.fastpix.data.domain.state.SDKStateService
import io.fastpix.data.domain.state.SessionService
import io.fastpix.data.domain.wallclock.ViewWatchCounter
import io.fastpix.data.sdkBuild.SDKBuildConfig
import io.fastpix.data.utils.Logger
import io.fastpix.data.utils.ScalingTracker

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


    /**
     * Initialize the FastPix SDK with the provided configuration
     *
     * @param config SDK configuration containing all required parameters
     * @param context Android application context
     * @throws IllegalStateException if SDK is already initialized
     * @throws IllegalArgumentException if configuration is invalid
     */
    fun initialize(config: SDKConfiguration, context: Context) {
        if (isInitialized) {
            throw IllegalStateException("FastPix SDK is already initialized")
        }
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

        // Initialize dependencies
        initializeDependencies()

        // Initialize session
        SessionService.initializeSession()

        // Initialize logging if enabled
        if (config.enableLogging) {
            Logger.enableLogging()
        }

        // Mark as initialized
        ViewWatchCounter.start()
        isInitialized = true
    }

    private fun initializeDependencies() {
        // Get dependencies from container
        SessionService.initializeSession()
        eventDispatcher = DependencyContainer.getEventDispatcher()
        sdkStateService = DependencyContainer.getSDKStateService()
        configuration?.let { config ->
            sdkStateService?.updateSDKConfiguration(config)
        }
    }

    /**
     * Check if the SDK is initialized
     */
    fun isInitialized(): Boolean = isInitialized

    /**
     * Get the current configuration
     */
    fun getConfiguration(): SDKConfiguration? = configuration

    /**
     * Get the player listener
     */
    fun getPlayerListener(): PlayerListener? = configuration?.playerListener

    /**
     * Set a temporary playhead time override for the next event
     */
    fun setPlayheadTimeOverride(playheadTime: Int?) {
        sdkStateService?.setPlayheadTimeOverride(playheadTime)
    }

    /**
     * Clear the playhead time override
     */
    fun clearPlayheadTimeOverride() {
        sdkStateService?.clearPlayheadTimeOverride()
    }

    /**
     * Dispatch a player event
     *
     * @param event The player event to dispatch
     * @throws IllegalStateException if SDK is not initialized
     */
    fun dispatchEvent(event: PlayerEventType, playheadTimeOverride: Int? = null) {
        if (!isInitialized) {
            throw IllegalStateException("FastPix SDK is not initialized")
        }
        val config = configuration ?: throw IllegalStateException("Configuration is null")
        if (SessionService.validateSession()) {
            when (event) {
                PlayerEventType.play -> {
                    ViewWatchCounter.start()
                    val playEvent = PlayEvent.createPlayEvent(config)
                    eventDispatcher?.dispatchEvent(playEvent.toJson())
                }
                PlayerEventType.viewBegin -> {
                    ViewWatchCounter.start()
                    val viewBeginEvent = ViewBeginEvent.createViewBeginEvent(config)
                    eventDispatcher?.dispatchEvent(viewBeginEvent.toJson())
                }

                PlayerEventType.playerReady -> {
                    ViewWatchCounter.start()
                    val playerReadyEvent = PlayerReadyEvent.createPlayerReadyEvent(config)
                    eventDispatcher?.dispatchEvent(playerReadyEvent.toJson())
                }

                PlayerEventType.seeked -> {
                    val seekedEvent = SeekedEvent.createSeekedEvent(config)
                    eventDispatcher?.dispatchEvent(seekedEvent.toJson())
                }

                PlayerEventType.variantChanged -> {
                    val variantChangeEvent = VariantChangedEvent.createVariantChangedEvent(config)
                    eventDispatcher?.dispatchEvent(variantChangeEvent.toJson())
                }

                PlayerEventType.playing -> {
                    ViewWatchCounter.start()
                    val playingEvent = PlayingEvent.createPlayingEvent(config)
                    eventDispatcher?.dispatchEvent(playingEvent.toJson())
                }

                PlayerEventType.seeking -> {
                    val seekingEvent = if (playheadTimeOverride != null) {
                        SeekingEvent.createSeekingEvent(config, playheadTimeOverride)
                    } else {
                        SeekingEvent.createSeekingEvent(config)
                    }
                    eventDispatcher?.dispatchEvent(seekingEvent.toJson())
                }

                PlayerEventType.pause -> {
                    ViewWatchCounter.pause()
                    val pauseEvent = if (playheadTimeOverride != null) {
                        PauseEvent.createPauseEvent(config, playheadTimeOverride)
                    } else {
                        PauseEvent.createPauseEvent(config)
                    }
                    eventDispatcher?.dispatchEvent(pauseEvent.toJson())
                }

                PlayerEventType.buffering -> {
                    ViewWatchCounter.start()
                    val bufferingEvent = BufferingEvent.createBufferingEvent(config)
                    eventDispatcher?.dispatchEvent(bufferingEvent.toJson())
                }

                PlayerEventType.buffered -> {
                    val bufferedEvent = BufferedEvent.createBufferedEvent(config)
                    eventDispatcher?.dispatchEvent(bufferedEvent.toJson())
                }

                PlayerEventType.ended -> {
                    ViewWatchCounter.pause()
                    val endedEvent = EndedEvent.createEndedEvent(config)
                    eventDispatcher?.dispatchEvent(endedEvent.toJson())
                }

                PlayerEventType.viewCompleted -> {
                    val viewCompletedEvent = ViewCompletedEvent.createViewCompletedEvent(config)
                    eventDispatcher?.dispatchEvent(viewCompletedEvent.toJson())
                }

                PlayerEventType.error -> {
                    val errorEvent = ErrorEvent.createErrorEvent(config)
                    eventDispatcher?.dispatchEvent(errorEvent.toJson())
                }

                PlayerEventType.requestCanceled -> {
                    val requestCancelled = RequestCancelledEvent.createRequestCancelledEvent(config)
                    eventDispatcher?.dispatchEvent(requestCancelled.toJson())
                }

                PlayerEventType.requestFailed -> {
                    val requestFailed = RequestFailedEvent.createRequestFailedEvent(config)
                    eventDispatcher?.dispatchEvent(requestFailed.toJson())
                }

                PlayerEventType.requestCompleted -> {
                    val requestCompleted = RequestCompletedEvent.createRequestCompletedEvent(config)
                    eventDispatcher?.dispatchEvent(requestCompleted.toJson())
                }
            }
        } else {
            SessionService.initializeSession()
            sdkStateService?.viewBeginCalled()
            val playerReadyEvent = PlayerReadyEvent.createPlayerReadyEvent(config)
            eventDispatcher?.dispatchEvent(playerReadyEvent.toJson())
            val viewBeginEvent = ViewBeginEvent.createViewBeginEvent(config)
            eventDispatcher?.dispatchEvent(viewBeginEvent.toJson())
        }

    }

    /**
     * Reset the SDK (for testing purposes)
     */
    fun release() {
        val viewCompletedEvent = ViewCompletedEvent.createViewCompletedEvent(configuration!!)
        eventDispatcher?.dispatchEvent(viewCompletedEvent.toJson())
        eventDispatcher?.cleanup {
            scalingTracker.reset()
            configuration = null
            isInitialized = false
            DependencyContainer.reset()
        }
    }
}