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
    val viewerId: String,
    val videoData: VideoDataDetails? = null,
    val playerListener: PlayerListener,
    val enableLogging: Boolean = true,
    val customData: CustomDataDetails? = null
) {
    init {
        // Validate mandatory parameters
        require(workspaceId.isNotBlank()) { "Workspace ID is mandatory and cannot be blank" }
        require(viewerId.isNotBlank()) { "Viewer ID is mandatory and cannot be blank" }

        // Validate video data (only videoId and videoTitle are mandatory)
        require(videoData?.videoId?.isNotBlank() == true) {
            "Video ID is mandatory and cannot be blank"
        }
        require(videoData.videoTitle?.isNotBlank() == true) {
            "Video title is mandatory and cannot be blank"
        }
    }
}