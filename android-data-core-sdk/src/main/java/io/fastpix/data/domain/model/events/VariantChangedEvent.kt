package io.fastpix.data.domain.model.events


import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.di.DependencyContainer
import kotlinx.serialization.SerialName

class VariantChangedEvent (
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
    @SerialName("vdsowt") var videoSourceWidth: String? = null,
    @SerialName("vdsoht") var videoSourceHeight: String? = null,
    @SerialName("vdid") var videoId: String? = null,
    @SerialName("vdsofs") var frameRate: String? = null,
    @SerialName("vdsomity") var mimeType: String? = null,
    @SerialName("vdsobi") var bitrate: String? = null,
    @SerialName("evna") var eventName: String = "variantChanged"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "vdsowt" to videoSourceWidth,
            "vdsoht" to videoSourceHeight,
            "vdid" to videoId,
            "vdsofs" to frameRate,
            "vdsomity" to mimeType,
            "vdsobi" to bitrate,
            "evna" to eventName,
        )
    }

    companion object {
        /**
         * Create a SeekingEvent with all data populated
         */
        fun createVariantChangedEvent(configService: SDKConfiguration): VariantChangedEvent {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val sdkState = sdkStateService.sdkState.value
            // Get base event data
            val baseData = getBaseEventData(configService)
            
            // Use configService.playerListener directly to avoid state synchronization issues
            // during rapid init/clear cycles
            val playerListener = configService.playerListener

            return VariantChangedEvent(
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
                videoSourceWidth = playerListener.videoSourceWidth().toString(),
                videoSourceHeight = playerListener.videoSourceHeight().toString(),
                videoId = sdkState.videoDataDetails?.videoId,
                frameRate = playerListener.sourceAdvertiseFrameRate().toString(),
                mimeType = playerListener.mimeType().toString(),
                bitrate = playerListener.sourceAdvertisedBitrate().toString(),

            )
        }
    }
}