package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.di.DependencyContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class EndedEvent(
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
    @SerialName("evna") var eventName: String = "ended"
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
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create an EndedEvent with all data populated
         */
        fun createEndedEvent(
            configService: SDKConfiguration,
            playheadTimeOverride: Int? = null
        ): EndedEvent {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val eventDataCalculator = DependencyContainer.getEventDataCalculator()
            val sdkState = sdkStateService.sdkState.value
            val baseData = getBaseEventData(configService)
            val playheadTime = playheadTimeOverride?.toString() ?: baseData["plphti"]
            return EndedEvent(
                workSpaceId = baseData["wsid"],
                viewId = baseData["veid"],
                viewSequenceNumber = baseData["vesqnu"],
                playerSequenceNumber = baseData["plsqnu"],
                beaconDomain = baseData["bedn"],
                playheadTime = playheadTime,
                viewerTimeStamp = baseData["vitp"],
                playerInstanceId = baseData["plinid"],
                viewWatchTime = baseData["vewati"],
                connectionType = baseData["vicity"],
                videoContentPlaybackTime = sdkState.viewTotalContentPlaybackTime.toString(),
                viewTotalContentPlayBackTime = sdkState.viewTotalContentPlaybackTime.toString(),
                viewRebufferDuration = eventDataCalculator.calculateRebufferDuration(),
                viewBufferFrequency = eventDataCalculator.calculateBufferFrequency(),
                viewBufferPercentage = eventDataCalculator.calculateBufferPercentage()
            )
        }
    }
}
