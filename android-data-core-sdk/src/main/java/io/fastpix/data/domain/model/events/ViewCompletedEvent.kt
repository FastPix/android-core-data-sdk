package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.scalingTracker
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ViewCompletedEvent(
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
    @SerialName("vectpbti") var videoContentPlaybackTime: String? = null,
    @SerialName("vetlctpbti") var viewTotalContentPlayBackTime: String? = null,
    @SerialName("verbdu") var viewRebufferDuration: String? = null,
    @SerialName("verbfq") var viewBufferFrequency: String? = null,
    @SerialName("verbpg") var viewBufferPercentage: String? = null,
    @SerialName("vemauppg") var viewMaxUpScalePercentage: String? = null,
    @SerialName("vemadopg") var viewMaxDownScalePercentage: String? = null,
    @SerialName("vetlug") var viewTotalUpScaling: String? = null,
    @SerialName("vetldg") var viewTotalDownScaling: String? = null,
    @SerialName("vdsodu") var videoDuration: String? = null,
    @SerialName("plwt") var playerWidth: String? = null,
    @SerialName("plht") var playerHeight: String? = null,
    @SerialName("rqvdwt") var videoWidth: String? = null,
    @SerialName("rqvdht") var videoHeight: String? = null,
    @SerialName("evna") var eventName: String = "viewCompleted"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "vectpbti" to videoContentPlaybackTime,
            "vetlctpbti" to viewTotalContentPlayBackTime,
            "verbdu" to viewRebufferDuration,
            "verbfq" to viewBufferFrequency,
            "verbpg" to viewBufferPercentage,
            "vemauppg" to viewMaxUpScalePercentage,
            "vemadopg" to viewMaxDownScalePercentage,
            "vetlug" to viewTotalUpScaling,
            "vetldg" to viewTotalDownScaling,
            "vdsodu" to videoDuration,
            "plwt" to playerWidth,
            "plht" to playerHeight,
            "vdsowt" to videoWidth,
            "vdsoht" to videoHeight,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a ViewCompletedEvent with all data populated
         */
        fun createViewCompletedEvent(configService: SDKConfiguration): ViewCompletedEvent {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val eventDataCalculator = DependencyContainer.getEventDataCalculator()
            val baseData = getBaseEventData(configService)
            
            // Use configService.playerListener directly to avoid state synchronization issues
            // during rapid init/clear cycles
            val playerListener = configService.playerListener
            val currentPlayheadTime = playerListener.playHeadTime() ?: 0
            scalingTracker.calculateScalingForCurrentInterval(currentPlayheadTime.toLong())

            return ViewCompletedEvent(
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
                videoContentPlaybackTime = scalingTracker.getTotalPlaybackTime().toString(),
                viewTotalContentPlayBackTime = scalingTracker.getTotalPlaybackTime().toString(),
                viewRebufferDuration = eventDataCalculator.calculateRebufferDuration(),
                viewBufferFrequency = eventDataCalculator.calculateBufferFrequency(),
                viewBufferPercentage = eventDataCalculator.calculateBufferPercentage(),
                viewMaxUpScalePercentage = scalingTracker.getCurrentMaxUpscale().toString(),
                viewMaxDownScalePercentage = scalingTracker.getCurrentMaxDownscale().toString(),
                viewTotalUpScaling = scalingTracker.getTotalUpscalingTimeWeighted().toString(),
                viewTotalDownScaling = scalingTracker.getTotalDownscalingTimeWeighted().toString(),
                videoDuration = sdkStateService.sdkState.value.playerListener?.sourceDuration()?.toString(),
                playerWidth = sdkStateService.sdkState.value.playerListener?.playerWidth()?.toString(),
                playerHeight = sdkStateService.sdkState.value.playerListener?.playerHeight()?.toString(),
                videoWidth = sdkStateService.sdkState.value.playerListener?.videoSourceWidth()?.toString(),
                videoHeight = sdkStateService.sdkState.value.playerListener?.videoSourceHeight()?.toString(),
            )
        }
    }
}
