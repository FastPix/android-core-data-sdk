package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.utils.Utils
import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.scalingTracker
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PlayingEvent(
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
    @SerialName("vetitofifr") var videoStartTime: String? = null,
    @SerialName("verbdu") var viewRebufferDuration: String? = null,
    @SerialName("verbfq") var viewBufferFrequency: String? = null,
    @SerialName("verbpg") var viewBufferPercentage: String? = null,
    @SerialName("plwt") var playerWidth: String? = null,
    @SerialName("plht") var playerHeight: String? = null,
    @SerialName("vdsodu") var videoDuration: String? = null,
    @SerialName("vdsowt") var videoSourceWidth: String? = null,
    @SerialName("vdsoht") var videoSourceHeight: String? = null,
    @SerialName("plisfl") var isPlayerFullScreen: String? = null,
    @SerialName("vdsour") var videoSourceUrl: String? = null,
    @SerialName("vdsohn") var videoHostName: String? = null,
    @SerialName("evna") var eventName: String = "playing"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "vetitofifr" to videoStartTime,
            "verbdu" to viewRebufferDuration,
            "verbfq" to viewBufferFrequency,
            "verbpg" to viewBufferPercentage,
            "plwt" to playerWidth,
            "plht" to playerHeight,
            "vdsodu" to videoDuration,
            "vdsowt" to videoSourceWidth,
            "vdsoht" to videoSourceHeight,
            "plisfl" to isPlayerFullScreen,
            "vdsour" to videoSourceUrl,
            "vdsohn" to videoHostName,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a PlayingEvent with all data populated
         */
        fun createPlayingEvent(configService: SDKConfiguration): PlayingEvent {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val eventDataCalculator = DependencyContainer.getEventDataCalculator()
            val sdkState = sdkStateService.sdkState.value
            
            // Use configService.playerListener directly to avoid state synchronization issues
            // during rapid init/clear cycles
            val playerListener = configService.playerListener
            val baseData = getBaseEventData(configService)
            val playerHeight = playerListener.playerHeight() ?: 0
            val playerWidth = playerListener.playerWidth() ?: 0
            val videoHeight = playerListener.videoSourceHeight() ?: 0
            val videoWidth = playerListener.videoSourceWidth() ?: 0
            if (playerListener.isFullScreen() == true) {
                sdkStateService.updateFullScreenUsed()
            }
            var calculateViewTimeToFirstFrame = 0L
            if (!sdkState.viewTimeToFirstFrameSent) {
                sdkStateService.updateViewTimeToFirstFrame()
                calculateViewTimeToFirstFrame = Utils.currentTimeStamp() - sdkState.viewBeginTime
            }
            scalingTracker.collectDataForScaling(
                currentPlayheadTime = (playerListener.playHeadTime() ?: 0).toLong(),
                playerWidth = playerWidth,
                playerHeight = playerHeight,
                videoSourceWidth = videoWidth,
                videoSourceHeight = videoHeight
            )
            return PlayingEvent(
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
                videoStartTime = calculateViewTimeToFirstFrame.toString(),
                viewRebufferDuration = eventDataCalculator.calculateRebufferDuration(),
                viewBufferFrequency = eventDataCalculator.calculateBufferFrequency(),
                viewBufferPercentage = eventDataCalculator.calculateBufferPercentage(),
                playerWidth = playerWidth.toString(),
                playerHeight = playerHeight.toString(),
                videoSourceWidth = videoWidth.toString(),
                videoSourceHeight = videoHeight.toString(),
                isPlayerFullScreen = sdkState.isFullScreen.toString(),
                videoDuration = playerListener.sourceDuration()?.toString(),
                videoSourceUrl = configService.videoData?.videoSourceUrl ?: configService.playerListener.sourceUrl(),
                videoHostName = Utils.getDomain(configService.videoData?.videoSourceUrl ?: configService.playerListener.sourceUrl()),
            )
        }
    }
}
