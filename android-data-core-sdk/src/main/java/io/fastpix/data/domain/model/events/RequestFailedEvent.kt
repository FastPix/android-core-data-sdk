package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.di.DependencyContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RequestFailedEvent(
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
    @SerialName("rqercd") var requestError: String? = null,
    @SerialName("rqhn") var requestHostName: String? = null,
    @SerialName("rqerte") var requestErrorText: String? = null,
    @SerialName("plisfl") var isPlayerFullScreen: String? = null,
    @SerialName("evna") var eventName: String = "requestFailed"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "reqid" to requestId,
            "rqur" to requestUrl,
            "rqty" to requestMethod,
            "rqercd" to requestError,
            "rqhn" to requestHostName,
            "rqerte" to requestErrorText,
            "plisfl" to isPlayerFullScreen,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a SeekingEvent with all data populated
         */
        fun createRequestFailedEvent(configService: SDKConfiguration): RequestFailedEvent {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val bandWidthModel = sdkStateService.sdkState.value.playerListener?.getBandWidthData()
            val baseData = getBaseEventData(configService)
            val playerListener = sdkStateService.sdkState.value.playerListener
            if (playerListener?.isFullScreen() == true) {
                sdkStateService.updateFullScreenUsed()
            }
            return RequestFailedEvent(
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
                requestMethod = bandWidthModel?.requestMethod,
                requestUrl = bandWidthModel?.requestUrl,
                requestError = bandWidthModel?.requestErrorCode,
                requestHostName = bandWidthModel?.requestHostName,
                requestErrorText = bandWidthModel?.requestErrorText,
                isPlayerFullScreen = sdkStateService.sdkState.value.isFullScreen.toString(),
            )
        }
    }
}