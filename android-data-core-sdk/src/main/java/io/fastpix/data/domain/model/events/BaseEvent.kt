package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.domain.wallclock.ViewWatchCounter
import io.fastpix.data.utils.Utils
import io.fastpix.data.di.DependencyContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
abstract class BaseEvent {
    @SerialName("wsid")
    abstract val workSpaceId: String?

    @SerialName("veid")
    abstract val viewId: String?

    @SerialName("vesqnu")
    abstract val viewSequenceNumber: String?

    @SerialName("plsqnu")
    abstract val playerSequenceNumber: Int?

    @SerialName("bedn")
    abstract val beaconDomain: String?

    @SerialName("plphti")
    abstract val playheadTime: Int?

    @SerialName("vitp")
    abstract val viewerTimeStamp: Long?

    @SerialName("plinid")
    abstract val playerInstanceId: String?

    @SerialName("vewati")
    abstract val viewWatchTime: String?

    @SerialName("vicity")
    abstract val connectionType: String?

    @SerialName("evna")
    abstract var eventName: String?

    @SerialName("plisfl")
    abstract var isPlayerFullScreen: String?
}


data class BaseEventData(
    val workSpaceId: String?,
    val viewId: String?,
    val viewSequenceNumber: String?,
    val playerSequenceNumber: Int?,
    val beaconDomain: String?,
    val playheadTime: Int?,
    val viewerTimeStamp: Long?,
    val playerInstanceId: String?,
    val viewWatchTime: String?,
    val connectionType: String?,
    var eventName: String?,
    var isPlayerFullScreen: String?
)


object BaseEventFactory {
    const val EMPTY_STRING = ""

    fun create(configService: SDKConfiguration): BaseEventData {
        val sdkStateService = DependencyContainer.getSDKStateService()
        val sdkState = sdkStateService.sdkState.value
        sdkStateService.viewSequenceNumber()
        sdkStateService.playerSequenceNumber()
        val viewId = sdkState.viewId
        val currentTimeStamp = Utils.currentTimeStamp()
        val playerObserver = configService.playerListener
        val sequenceNumber = sdkState.viewSequenceNumber
        val playerSequenceNumber = sdkState.playerSequenceNumber
        val workSpaceId = configService.workspaceId
        val playerId = sdkState.playerId
        val playHeadTime = (sdkState.playheadTimeOverride ?: playerObserver.playHeadTime()) ?: 0
        val viewWatchTime = ViewWatchCounter.value.toString()
        val connectionType = Utils.checkNetworkType() ?: sdkState.connectionType
        if (playerObserver.isFullScreen() == true) {
            sdkStateService.updateFullScreenUsed()
        }

        return BaseEventData(
            workSpaceId = workSpaceId,
            viewId = viewId,
            viewSequenceNumber = sequenceNumber.toString(),
            playerSequenceNumber = playerSequenceNumber,
            beaconDomain = configService.beaconUrl ?: "metrix.ws",
            playheadTime = playHeadTime,
            viewerTimeStamp = currentTimeStamp,
            playerInstanceId = playerId,
            viewWatchTime = viewWatchTime,
            connectionType = connectionType,
            eventName = EMPTY_STRING,
            isPlayerFullScreen = sdkState.isFullScreen.toString()
        )
    }
}
