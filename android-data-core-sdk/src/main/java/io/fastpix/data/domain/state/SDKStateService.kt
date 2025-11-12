package io.fastpix.data.domain.state

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.utils.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID


class SDKStateService {
    private val _sdkState = MutableStateFlow(SDKState())

    val sdkState: StateFlow<SDKState> = _sdkState.asStateFlow()

    init {
        _sdkState.value = _sdkState.value.copy(
            sessionId = UUID.randomUUID().toString(),
            playerId = Utils.randomV4(),
            viewId = UUID.randomUUID().toString()
        )
    }

    fun viewBeginCalled() {
        _sdkState.value = _sdkState.value.copy(isViewBeginCalled = true)
    }

    fun viewSequenceNumber() {
        var lastViewSequenceNumber = _sdkState.value.viewSequenceNumber
        lastViewSequenceNumber += 1
        _sdkState.value = _sdkState.value.copy(viewSequenceNumber = lastViewSequenceNumber)
    }

    fun playerSequenceNumber() {
        var lastPlayerSequenceNumber = _sdkState.value.playerSequenceNumber
        lastPlayerSequenceNumber += 1
        _sdkState.value = _sdkState.value.copy(playerSequenceNumber = lastPlayerSequenceNumber)
    }

    fun updateSDKConfiguration(config: SDKConfiguration) {
        _sdkState.value = _sdkState.value.copy(
            sdkConfiguration = config,
            playerDataDetails = config.playerData,
            videoDataDetails = config.videoData,
            playerListener = config.playerListener,
            workSpaceId = config.workspaceId,
            beaconUrl = if (config.beaconUrl?.isEmpty() == true) "metrix.ws" else config.beaconUrl,
            baseUrl = "https://${config.workspaceId}.${if (config.beaconUrl?.isEmpty() == true) "metrix.ws" else config.beaconUrl}"
        )
    }

    /**
     * Update connection type in the SDK state
     */
    fun updateConnectionType(connectionType: String) {
        _sdkState.value = _sdkState.value.copy(connectionType = connectionType)
    }

    /**
     * Get current connection type
     */
    fun getConnectionType(): String? {
        return _sdkState.value.connectionType
    }

    fun clearSdkState() {
        _sdkState.value = SDKState(
            sessionId = UUID.randomUUID().toString(),
            playerId = Utils.randomV4(),
            viewId = UUID.randomUUID().toString()
        )
    }
    
    /**
     * Get current session ID
     */
    fun getSessionId(): String? {
        return _sdkState.value.sessionId
    }

    fun updateViewBeginTime() {
        _sdkState.value = _sdkState.value.copy(
            viewBeginTime = System.currentTimeMillis()
        )
    }

    fun updateViewTimeToFirstFrame() {
        _sdkState.value = _sdkState.value.copy(
            viewTimeToFirstFrameSent = true
        )
    }

    fun updateFullScreenUsed() {
        _sdkState.value = _sdkState.value.copy(
            isFullScreen = true
        )
    }

    /**
     * Update scaling metrics in the SDK state
     */
    fun updateScalingMetrics(
        viewMaxUpScalePercentage: Double?,
        viewMaxDownScalePercentage: Double?,
        viewTotalUpScaling: Double?,
        viewTotalDownScaling: Double?,
        viewTotalContentPlaybackTime: Long?
    ) {
        _sdkState.value = _sdkState.value.copy(
            viewMaxUpScalePercentage = viewMaxUpScalePercentage,
            viewMaxDownScalePercentage = viewMaxDownScalePercentage,
            viewTotalUpScaling = viewTotalUpScaling,
            viewTotalDownScaling = viewTotalDownScaling,
            viewTotalContentPlaybackTime = viewTotalContentPlaybackTime
        )
    }

    /**
     * Set a temporary playhead time override for the next event
     */
    fun setPlayheadTimeOverride(playheadTime: Int?) {
        _sdkState.value = _sdkState.value.copy(playheadTimeOverride = playheadTime)
    }

    /**
     * Clear the playhead time override
     */
    fun clearPlayheadTimeOverride() {
        _sdkState.value = _sdkState.value.copy(playheadTimeOverride = null)
    }

    /**
     * Get the current playhead time override
     */
    fun getPlayheadTimeOverride(): Int? {
        return _sdkState.value.playheadTimeOverride
    }

}