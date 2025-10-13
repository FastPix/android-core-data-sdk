package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.domain.state.SessionService
import io.fastpix.data.scalingTracker
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PauseEvent(
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
    @SerialName("vemauppg") var viewMaxUpScalePercentage: String? = null,
    @SerialName("vemadopg") var viewMaxDownScalePercentage: String? = null,
    @SerialName("vetlug") var viewTotalUpScaling: String? = null,
    @SerialName("vetldg") var viewTotalDownScaling: String? = null,
    @SerialName("vetlctpbti") var viewTotalContentPlayBackTime: String? = null,
    @SerialName("plispu") var playerIsPaused: String? = null,
    @SerialName("snepti") var sessionExpiredTime: String? = null,
    @SerialName("evna") var eventName: String = "pause"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "vetlctpbti" to viewTotalContentPlayBackTime,
            "plispu" to playerIsPaused,
            "snepti" to sessionExpiredTime,
            "vemauppg" to viewMaxUpScalePercentage,
            "vemadopg" to viewMaxDownScalePercentage,
            "vetlug" to viewTotalUpScaling,
            "vetldg" to viewTotalDownScaling,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a PauseEvent with all data populated
         */
        fun createPauseEvent(
            configService: SDKConfiguration,
            playheadTimeOverride: Int? = null
        ): PauseEvent {
            val baseData = getBaseEventData(configService)
            val playheadTime = playheadTimeOverride?.toString() ?: baseData["plphti"]
            
            // Use configService.playerListener directly to avoid state synchronization issues
            // during rapid init/clear cycles
            val playerListener = configService.playerListener
            val currentPlayheadTime = playheadTimeOverride ?: playerListener.playHeadTime() ?: 0
            scalingTracker.calculateScalingForCurrentInterval(currentPlayheadTime.toLong())
            return PauseEvent(
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
                viewTotalContentPlayBackTime = scalingTracker.getTotalPlaybackTime().toString(),
                playerIsPaused = playerListener.isPause()?.toString(),
                sessionExpiredTime = SessionService.getSessionExpireTime()?.toString(),
                viewMaxUpScalePercentage = scalingTracker.getCurrentMaxUpscale().toString(),
                viewMaxDownScalePercentage = scalingTracker.getCurrentMaxDownscale().toString(),
                viewTotalUpScaling = scalingTracker.getTotalUpscalingTimeWeighted().toString(),
                viewTotalDownScaling = scalingTracker.getTotalDownscalingTimeWeighted().toString(),
            )
        }
    }
}
