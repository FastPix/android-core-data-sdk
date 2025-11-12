package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.utils.Utils
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PlayEvent(
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
    @SerialName("vdid") var videoId: String? = null,
    @SerialName("verbdu") var viewRebufferDuration: String? = null,
    @SerialName("verbfq") var viewBufferFrequency: String? = null,
    @SerialName("verbpg") var viewBufferPercentage: String? = null,
    @SerialName("vdsodu") var videoDuration: String? = null,
    @SerialName("plwt") var playerWidth: String? = null,
    @SerialName("plht") var playerHeight: String? = null,
    @SerialName("rqvdwt") var videoWidth: String? = null,
    @SerialName("rqvdht") var videoHeight: String? = null,
    @SerialName("vdsour") var videoSourceUrl: String? = null,
    @SerialName("vdsohn") var videoHostName: String? = null,
    @SerialName("evna") var eventName: String = "play"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "vdid" to videoId,
            "verbdu" to viewRebufferDuration,
            "verbfq" to viewBufferFrequency,
            "verbpg" to viewBufferPercentage,
            "vdsodu" to videoDuration,
            "plwt" to playerWidth,
            "plht" to playerHeight,
            "vdsowt" to videoWidth,
            "vdsoht" to videoHeight,
            "vdsour" to videoSourceUrl,
            "vdsohn" to videoHostName,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a PlayEvent with all data populated
         */
        fun createPlayEvent(configService: SDKConfiguration): PlayEvent {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val eventDataCalculator = DependencyContainer.getEventDataCalculator()
            val baseData = getBaseEventData(configService)
            return PlayEvent(
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
                videoId = configService.videoData?.videoId,
                viewRebufferDuration = eventDataCalculator.calculateRebufferDuration(),
                viewBufferFrequency = eventDataCalculator.calculateBufferFrequency(),
                videoDuration = sdkStateService.sdkState.value.playerListener?.sourceDuration()?.toString(),
                viewBufferPercentage = eventDataCalculator.calculateBufferPercentage(),
                playerWidth = sdkStateService.sdkState.value.playerListener?.playerWidth()?.toString(),
                playerHeight = sdkStateService.sdkState.value.playerListener?.playerHeight()?.toString(),
                videoWidth = sdkStateService.sdkState.value.playerListener?.videoSourceWidth()?.toString(),
                videoHeight = sdkStateService.sdkState.value.playerListener?.videoSourceHeight()?.toString(),
                videoSourceUrl = configService.videoData?.videoSourceUrl ?: configService.playerListener.sourceUrl(),
                videoHostName = Utils.getDomain(configService.videoData?.videoSourceUrl ?: configService.playerListener.sourceUrl()),
            )
        }
    }
}
