package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.di.DependencyContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PlayerReadyEvent(
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
    @SerialName("plitti") var playerInitTime: String? = null,
    @SerialName("plwt") var playerWidth: String? = null,
    @SerialName("plht") var playerHeight: String? = null,
    @SerialName("vdsodu") var videoDuration: String? = null,
    @SerialName("evna") var eventName: String = "playerReady"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "plitti" to playerInitTime,
            "plwt" to playerWidth,
            "plht" to playerHeight,
            "vdsodu" to videoDuration,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a SeekingEvent with all data populated
         */
        fun createPlayerReadyEvent(configService: SDKConfiguration): PlayerReadyEvent {
            val stateService = DependencyContainer.getSDKStateService()
            val baseData = getBaseEventData(configService)
            val playerInitTime = System.currentTimeMillis() - stateService.sdkState.value.viewBeginTime
            val playerListener = configService.playerListener
            return PlayerReadyEvent(
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
                playerWidth = playerListener.playerWidth()?.toString(),
                playerHeight = playerListener.playerHeight()?.toString(),
                videoDuration = playerListener.sourceDuration()?.toString(),
                playerInitTime = playerInitTime.toString()
            )
        }
    }
}