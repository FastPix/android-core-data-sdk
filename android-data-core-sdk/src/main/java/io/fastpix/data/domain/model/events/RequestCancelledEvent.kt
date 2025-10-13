package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.di.DependencyContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RequestCancelledEvent(
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
    @SerialName("rqid") var requestId: String? = null,
    @SerialName("rqur") var requestUrl: String? = null,
    @SerialName("rqty") var requestMethod: String? = null,
    @SerialName("rqhn") var requestHostName: String? = null,
    @SerialName("rqca") var requestCancel: String? = null,
    @SerialName("plisfl") var isPlayerFullScreen: String? = null,
    @SerialName("evna") var eventName: String = "requestCanceled"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "rqid" to requestId,
            "rqur" to requestUrl,
            "rqty" to requestMethod,
            "rqhn" to requestHostName,
            "rqca" to requestCancel,
            "plisfl" to isPlayerFullScreen,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a SeekingEvent with all data populated
         */
        fun createRequestCancelledEvent(configService: SDKConfiguration): RequestCancelledEvent {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val bandWidthModel = sdkStateService.sdkState.value.playerListener?.getBandWidthData()
            val baseData = getBaseEventData(configService)
            val playerListener = sdkStateService.sdkState.value.playerListener
            if (playerListener?.isFullScreen() == true) {
                sdkStateService.updateFullScreenUsed()
            }
            return RequestCancelledEvent(
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
                requestId = bandWidthModel?.requestId,
                requestUrl = bandWidthModel?.requestUrl,
                requestMethod = bandWidthModel?.requestMethod,
                requestHostName = bandWidthModel?.requestHostName,
                requestCancel = bandWidthModel?.requestCancel,
                isPlayerFullScreen = sdkStateService.sdkState.value.isFullScreen.toString(),
            )
        }
    }
}