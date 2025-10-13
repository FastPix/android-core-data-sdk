package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.domain.wallclock.ViewWatchCounter
import io.fastpix.data.utils.Utils
import io.fastpix.data.di.DependencyContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class BaseEvent(
    @SerialName("wsid")
    open var workSpaceId: String? = null,
    @SerialName("veid")
    open var viewId: String? = null,
    @SerialName("vesqnu")
    open var viewSequenceNumber: String? = null,
    @SerialName("plsqnu")
    open var playerSequenceNumber: String? = null,
    @SerialName("bedn")
    open var beaconDomain: String? = null,
    @SerialName("plphti")
    open var playheadTime: String? = null,
    @SerialName("vitp")
    open var viewerTimeStamp: String? = null,
    @SerialName("plinid")
    open var playerInstanceId: String? = null,
    @SerialName("vewati")
    open var viewWatchTime: String? = null,
    @SerialName("vicity")
    open var connectionType: String? = null
) {
    
    /**
     * Convert the event to a map of key-value pairs for JSON serialization
     */
    open fun toJson(): Map<String, String?> {
        return mapOf(
            "wsid" to workSpaceId,
            "veid" to viewId,
            "vesqnu" to viewSequenceNumber,
            "plsqnu" to playerSequenceNumber,
            "bedn" to beaconDomain,
            "plphti" to playheadTime,
            "vitp" to viewerTimeStamp,
            "plinid" to playerInstanceId,
            "vewati" to viewWatchTime,
            "vicity" to connectionType
        )
    }
    
    companion object {
        /**
         * Get base event data populated from configuration and current state
         * This is equivalent to the Dart getBaseEventData method
         */
        fun getBaseEventData(configService: SDKConfiguration): Map<String, String?> {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val sdkState = sdkStateService.sdkState.value
            sdkStateService.viewSequenceNumber()
            sdkStateService.playerSequenceNumber()
            // Generate new view ID for each event
            val viewId = sdkState.viewId
            val currentTimeStamp = Utils.currentTimeStamp()
            val playerObserver = configService.playerListener
            val sequenceNumber = sdkState.viewSequenceNumber
            val playerSequenceNumber = sdkState.playerSequenceNumber
            val workSpaceId = configService.workspaceId
            // Use configService.beaconUrl directly to avoid state synchronization issues during rapid init/clear cycles
            val beaconDomain = if (configService.beaconUrl?.isNotEmpty() == true) configService.beaconUrl else "metrix.ws"
            val playerId = sdkState.playerId
            val playHeadTime = sdkState.playheadTimeOverride?.toString() ?: playerObserver.playHeadTime()?.toString()
            val viewWatchTime = ViewWatchCounter.value.toString()
            
            // Get connection type and update if needed
            val connectionType = Utils.checkNetworkType() ?: sdkState.connectionType
            
            return mapOf(
                "wsid" to workSpaceId,
                "veid" to viewId,
                "vesqnu" to sequenceNumber.toString(),
                "plsqnu" to playerSequenceNumber.toString(),
                "bedn" to beaconDomain,
                "plphti" to playHeadTime,
                "vitp" to currentTimeStamp.toString(),
                "plinid" to playerId,
                "vewati" to viewWatchTime,
                "vicity" to connectionType
            )
        }
    }
}