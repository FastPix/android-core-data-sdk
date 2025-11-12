package io.fastpix.data.domain.state

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.domain.listeners.PlayerListener
import io.fastpix.data.domain.model.CustomDataDetails
import io.fastpix.data.domain.model.PlayerDataDetails
import io.fastpix.data.domain.model.VideoDataDetails

data class SDKState(
    val sessionId: String? = null,
    val playerDataDetails: PlayerDataDetails? = null,
    val videoDataDetails: VideoDataDetails? = null,
    val playerListener: PlayerListener? = null,
    val isViewBeginCalled: Boolean = false,
    val playerId: String? = null,
    val workSpaceId: String? = null,
    val beaconUrl: String? = null,
    val baseUrl: String? = null,
    val viewId: String? = null,
    val connectionType: String? = null,
    val seekCount: Int? = null,
    val customDataDetails: CustomDataDetails? = null,
    val viewSequenceNumber: Int = 0,
    val playerSequenceNumber: Int = 0,
    val viewMaxUpScalePercentage: Double? = null,
    val viewMaxDownScalePercentage: Double? = null,
    val viewTotalUpScaling: Double? = null,
    val viewTotalDownScaling: Double? = null,
    val viewTotalContentPlaybackTime: Long? = null,
    val viewTimeToFirstFrameSent: Boolean = false,
    val viewBeginTime: Long = 0,
    val sdkConfiguration: SDKConfiguration? = null,
    val isFullScreen: Boolean = false,
    val playheadTimeOverride: Int? = null
)