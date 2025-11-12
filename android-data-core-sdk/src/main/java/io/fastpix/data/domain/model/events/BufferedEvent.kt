package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.di.DependencyContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BufferedEvent(
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
    @SerialName("verbdu") var viewRebufferDuration: String? = null,
    @SerialName("verbfq") var viewBufferFrequency: String? = null,
    @SerialName("verbpg") var viewBufferPercentage: String? = null,
    @SerialName("evna") var eventName: String = "buffered"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "verbdu" to viewRebufferDuration,
            "verbfq" to viewBufferFrequency,
            "verbpg" to viewBufferPercentage,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a BufferedEvent with all data populated
         */
        fun createBufferedEvent(configService: SDKConfiguration): BufferedEvent {
            val eventDataCalculator = DependencyContainer.getEventDataCalculator()
            val baseData = getBaseEventData(configService)
            eventDataCalculator.onBufferedEvent()

            return BufferedEvent(
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
                viewRebufferDuration = eventDataCalculator.calculateRebufferDuration(),
                viewBufferFrequency = eventDataCalculator.calculateBufferFrequency(),
                viewBufferPercentage = eventDataCalculator.calculateBufferPercentage(),
            )
        }
    }
}
