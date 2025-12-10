package io.fastpix.data.domain

import io.fastpix.data.domain.listeners.PlayerListener
import io.fastpix.data.domain.model.CustomDataDetails
import io.fastpix.data.domain.model.PlayerDataDetails
import io.fastpix.data.domain.model.VideoDataDetails

/**
 * Configuration class for FastPix SDK initialization
 * All parameters are mandatory except enableLogging and customData
 */
data class SDKConfiguration(
    val playerData: PlayerDataDetails? = null,
    val workspaceId: String,
    val beaconUrl: String? = null,
    val videoData: VideoDataDetails? = null,
    val playerListener: PlayerListener,
    val enableLogging: Boolean = true,
    val customData: CustomDataDetails? = null
) {
    init {
        // Validate mandatory parameters
        require(workspaceId.isNotBlank()) { "Workspace ID is mandatory and cannot be blank" }
    }
}