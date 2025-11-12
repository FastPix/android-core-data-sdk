package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.scalingTracker
import io.fastpix.data.utils.Utils
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PulseEvent(
    override var workSpaceId: String? = null,
    override var viewId: String? = null,
    override var viewSequenceNumber: String? = null,
    override var playerSequenceNumber: String? = null,
    override var beaconDomain: String? = null,
    override var playheadTime: String? = null,
    override var viewerTimeStamp: String? = null,
    override var playerInstanceId: String? = null,
    override var viewWatchTime: String? = null,
    override var connectionType: String? = null,
    @SerialName("plisfl") var isPlayerFullScreen: String? = null,
    @SerialName("verbfq") var viewBufferFrequency: String? = null,
    @SerialName("verbpg") var viewBufferPercentage: String? = null,
    @SerialName("plwt") var playerWidth: String? = null,
    @SerialName("plht") var playerHeight: String? = null,
    @SerialName("rqvdwt") var videoWidth: String? = null,
    @SerialName("rqvdht") var videoHeight: String? = null,
    @SerialName("verbdu") var viewRebufferDuration: String? = null,
    @SerialName("vdsodu") var videoDuration: String? = null,
    @SerialName("vetlctpbti") var viewTotalContentPlayBackTime: String? = null,
    @SerialName("vemauppg") var viewMaxUpScalePercentage: String? = null,
    @SerialName("vemadopg") var viewMaxDownScalePercentage: String? = null,
    @SerialName("vetlug") var viewTotalUpScaling: String? = null,
    @SerialName("vetldg") var viewTotalDownScaling: String? = null,
    @SerialName("vdsour") var videoSourceUrl: String? = null,
    @SerialName("vdsohn") var videoHostName: String? = null,
    @SerialName("evna") var eventName: String = "pulse"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "plisfl" to isPlayerFullScreen,
            "verbfq" to viewBufferFrequency,
            "verbpg" to viewBufferPercentage,
            "plwt" to playerWidth,
            "plht" to playerHeight,
            "rqvdwt" to videoWidth,
            "rqvdht" to videoHeight,
            "vdsodu" to videoDuration,
            "verbdu" to viewRebufferDuration,
            "vetlctpbti" to viewTotalContentPlayBackTime,
            "vemauppg" to viewMaxUpScalePercentage,
            "vemadopg" to viewMaxDownScalePercentage,
            "vetlug" to viewTotalUpScaling,
            "vetldg" to viewTotalDownScaling,
            "vdsour" to videoSourceUrl,
            "vdsohn" to videoHostName,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a PulseEvent with all data populated
         */
        fun createPulseEvent(configService: SDKConfiguration): PulseEvent {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val eventDataCalculator = DependencyContainer.getEventDataCalculator()
            val sdkState = sdkStateService.sdkState.value
            
            // Use configService.playerListener directly to avoid state synchronization issues
            // during rapid init/clear cycles
            val playerListener = configService.playerListener
            if (playerListener.isFullScreen() == true) {
                sdkStateService.updateFullScreenUsed()
            }
            val currentPlayheadTime = playerListener.playHeadTime() ?: 0
            val playerHeight = playerListener.playerHeight() ?: 0
            val playerWidth = playerListener.playerWidth() ?: 0
            val videoHeight = playerListener.videoSourceHeight() ?: 0
            val videoWidth = playerListener.videoSourceWidth() ?: 0
            scalingTracker.collectDataForScaling(
                currentPlayheadTime = currentPlayheadTime.toLong(),
                playerWidth = playerWidth,
                playerHeight = playerHeight,
                videoSourceWidth = videoWidth,
                videoSourceHeight = videoHeight
            )
            // Get base event data
            val baseData = getBaseEventData(configService)

            return PulseEvent(
                workSpaceId = baseData["wsid"],
                viewId = baseData["veid"],
                viewSequenceNumber = baseData["vesqnu"],
                playerSequenceNumber = baseData["plsqnu"],
                beaconDomain = baseData["bedn"],
                playheadTime = baseData["plphti"],
                viewerTimeStamp = baseData["vitp"],
                playerInstanceId = baseData["plinid"],
                viewWatchTime = baseData["vewati"],
                connectionType = baseData["vicity"],
                isPlayerFullScreen = sdkState.isFullScreen.toString(),
                viewBufferFrequency = eventDataCalculator.calculateBufferFrequency(),
                viewRebufferDuration = eventDataCalculator.calculateRebufferDuration(),
                viewBufferPercentage = eventDataCalculator.calculateBufferPercentage(),
                playerWidth = playerListener.playerWidth()?.toString(),
                playerHeight = playerListener.playerHeight()?.toString(),
                videoDuration = playerListener.sourceDuration()?.toString(),
                videoWidth = playerListener.videoSourceWidth()?.toString(),
                videoHeight = playerListener.videoSourceHeight()?.toString(),
                viewTotalContentPlayBackTime = scalingTracker.getTotalPlaybackTime().toString(),
                viewMaxUpScalePercentage = scalingTracker.getCurrentMaxUpscale().toString(),
                viewMaxDownScalePercentage = scalingTracker.getCurrentMaxDownscale().toString(),
                viewTotalUpScaling = scalingTracker.getTotalUpscalingTimeWeighted().toString(),
                viewTotalDownScaling = scalingTracker.getTotalDownscalingTimeWeighted().toString(),
                videoSourceUrl = configService.videoData?.videoSourceUrl ?: configService.playerListener.sourceUrl(),
                videoHostName = Utils.getDomain(configService.videoData?.videoSourceUrl ?: configService.playerListener.sourceUrl()),
            )
        }
    }
}
