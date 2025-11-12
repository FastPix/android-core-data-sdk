package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.domain.wallclock.ViewWatchCounter
import io.fastpix.data.di.DependencyContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SeekedEvent(
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
    @SerialName("vesedu") var viewSeekDuration: String? = null,
    @SerialName("vemaseti") var viewMaxSeekDuration: String? = null,
    @SerialName("veseco") var viewSeekCount: String? = null,
    @SerialName("evna") var eventName: String = "seeked"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "vesedu" to viewSeekDuration,
            "vemaseti" to viewMaxSeekDuration,
            "veseco" to viewSeekCount,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a SeekedEvent with all data populated
         * This is equivalent to the Dart createSeekedEvent method
         */
        fun createSeekedEvent(configService: SDKConfiguration): SeekedEvent {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val eventDataCalculator = DependencyContainer.getEventDataCalculator()
            val playerListener = sdkStateService.sdkState.value.playerListener
//            if(playerListener?.isPause() == true){
//                ViewWatchCounter.pause()
//            } else {
//                ViewWatchCounter.start()
//            }
            val baseData = getBaseEventData(configService)
            eventDataCalculator.onSeekedEvent()
            val seekDuration = eventDataCalculator.calculateTotalSeekDuration()
            val seekCount = eventDataCalculator.calculateSeekCount()
            return SeekedEvent(
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
                viewSeekDuration = seekDuration,
                viewMaxSeekDuration = seekDuration,
                viewSeekCount = seekCount
            )
        }
    }
}
