package io.fastpix.data.utils

/**
 * Single Kotlin utility class for calculating video scaling percentages.
 * Calculates all four scaling parameters exactly as implemented in AdaptiveScalingMonitor.
 */
class ScalingCalculationUtil {

    /**
     * Data class to hold all scaling calculation results.
     */
    data class ScalingResult(
        val upscalePercentage: Double,           // Current upscale percentage
        val downscalePercentage: Double,          // Current downscale percentage
        val maxUpscalePercentage: Double,         // Maximum upscale percentage over time
        val maxDownscalePercentage: Double,        // Maximum downscale percentage over time
        val scalingFactor: Double,               // Overall scaling factor
        val totalPlaybackTime: Long,              // Total playback time accumulated
        val totalUpscalingTimeWeighted: Double,  // Total upscaling time-weighted
        val totalDownscalingTimeWeighted: Double // Total downscaling time-weighted
    ) {
        override fun toString(): String {
            return "ScalingResult(" +
                "upscale=${upscalePercentage.format(4)}, " +
                "downscale=${downscalePercentage.format(4)}, " +
                "maxUpscale=${maxUpscalePercentage.format(4)}, " +
                "maxDownscale=${maxDownscalePercentage.format(4)}, " +
                "factor=${scalingFactor.format(4)}, " +
                "time=${totalPlaybackTime}ms, " +
                "upscaleWeighted=${totalUpscalingTimeWeighted.format(2)}, " +
                "downscaleWeighted=${totalDownscalingTimeWeighted.format(2)})"
        }
    }

    /**
     * Calculates scaling metrics exactly as implemented in AdaptiveScalingMonitor.
     *
     * @param playerWidth Current player width in pixels
     * @param playerHeight Current player height in pixels
     * @param videoSourceWidth Original video source width in pixels
     * @param videoSourceHeight Original video source height in pixels
     * @param currentMaxUpscale Current maximum upscale percentage from previous calculations
     * @param currentMaxDownscale Current maximum downscale percentage from previous calculations
     * @param timeDelta Time duration in milliseconds for this scaling state (playhead time difference)
     * @param totalPlaybackTime Total playback time accumulated so far
     * @param totalUpscalingTimeWeighted Total upscaling time-weighted value accumulated so far
     * @param totalDownscalingTimeWeighted Total downscaling time-weighted value accumulated so far
     * @return ScalingResult containing all calculated values
     */
    companion object {
        fun calculateScalingMetrics(
            playerWidth: Int,
            playerHeight: Int,
            videoSourceWidth: Int,
            videoSourceHeight: Int,
            currentMaxUpscale: Double,
            currentMaxDownscale: Double,
            timeDelta: Long,
            totalPlaybackTime: Long,
            totalUpscalingTimeWeighted: Double,
            totalDownscalingTimeWeighted: Double
        ): ScalingResult {

            // Exact same calculation as AdaptiveScalingMonitor.CallToChangeTime() [Reference code equivalent]
            val widthOfSourceWidth = playerWidth.toDouble() / videoSourceWidth.toDouble()
            val heightOfSourceHeight = playerHeight.toDouble() / videoSourceHeight.toDouble()
            val minDifferenceFeatherweight = minOf(widthOfSourceWidth, heightOfSourceHeight)

            // Calculate current scaling percentages (exact same as original)
            val upscalePercentage = maxOf(0.0, minDifferenceFeatherweight - 1.0)  // Upscale percentage
            val downscalePercentage = maxOf(0.0, 1.0 - minDifferenceFeatherweight)   // Downscale percentage

            // Update maximum values (exact same as original)
            val maxUpscalePercentage = maxOf(currentMaxUpscale, upscalePercentage)        // Max upscale
            val maxDownscalePercentage = maxOf(currentMaxDownscale, downscalePercentage)      // Max downscale

            // Update time-weighted values (exact same as original)
            val newTotalPlaybackTime = totalPlaybackTime + timeDelta
            val newTotalUpscalingTimeWeighted = totalUpscalingTimeWeighted + (upscalePercentage * timeDelta)
            val newTotalDownscalingTimeWeighted = totalDownscalingTimeWeighted + (downscalePercentage * timeDelta)

            return ScalingResult(
                upscalePercentage = upscalePercentage,
                downscalePercentage = downscalePercentage,
                maxUpscalePercentage = maxUpscalePercentage,
                maxDownscalePercentage = maxDownscalePercentage,
                scalingFactor = minDifferenceFeatherweight,
                totalPlaybackTime = newTotalPlaybackTime,
                totalUpscalingTimeWeighted = newTotalUpscalingTimeWeighted,
                totalDownscalingTimeWeighted = newTotalDownscalingTimeWeighted
            )
        }
    }
}

/**
 * Kotlin scaling tracker that maintains state over time.
 * Use this to track scaling metrics throughout a video session.
 * 
 * This implementation follows the two-phase approach from AdaptiveScalingMonitor:
 * Phase 1 - Data Collection: Store interval start time and dimensions on "playing"/"pulse" events
 * Phase 2 - Scaling Calculation: Calculate scaling and update metrics on "pause"/"buffering"/etc events
 */
class ScalingTracker {

    // Accumulated tracking variables (persist across multiple intervals)
    private var currentMaxUpscale: Double = 0.0
    private var currentMaxDownscale: Double = 0.0
    private var totalPlaybackTime: Long = 0L
    private var totalUpscalingTimeWeighted: Double = 0.0
    private var totalDownscalingTimeWeighted: Double = 0.0
    
    // Current interval tracking variables (reset after each calculation)
    private var intervalStartTime: Long? = null
    private var storedPlayerWidth: Int? = null
    private var storedPlayerHeight: Int? = null
    private var storedVideoSourceWidth: Int? = null
    private var storedVideoSourceHeight: Int? = null

    /**
     * Legacy method for backward compatibility - now mapped to data collection
     * @deprecated Use collectDataForScaling() instead for proper two-phase behavior
     */
    @Deprecated("Use collectDataForScaling() for proper two-phase behavior",
                ReplaceWith("collectDataForScaling(playHeadTime.toLong(), 0, 0, 0, 0)"))
    fun setPlayerPlayHeadTime(playHeadTime: Int) {
        intervalStartTime = playHeadTime.toLong()
    }

    /**
     * Legacy method for backward compatibility
     * @deprecated No longer used in two-phase approach
     */
    @Deprecated("Use getCurrentIntervalStartTime() instead")
    fun getPlayerPlayHeadTime(): Int {
        return intervalStartTime?.toInt() ?: -1
    }

    /**
     * Phase 1: Data Collection - Called on "playing" or "pulse" events
     * Stores the current playhead time as interval start time and current dimensions.
     * Does NOT calculate scaling.
     *
     * @param currentPlayheadTime Current playhead time in milliseconds
     * @param playerWidth Current player width in pixels
     * @param playerHeight Current player height in pixels
     * @param videoSourceWidth Current video source width in pixels
     * @param videoSourceHeight Current video source height in pixels
     */
    fun collectDataForScaling(
        currentPlayheadTime: Long,
        playerWidth: Int,
        playerHeight: Int,
        videoSourceWidth: Int,
        videoSourceHeight: Int
    ) {
        intervalStartTime = currentPlayheadTime
        storedPlayerWidth = playerWidth
        storedPlayerHeight = playerHeight
        storedVideoSourceWidth = videoSourceWidth
        storedVideoSourceHeight = videoSourceHeight
    }

    /**
     * Phase 2: Scaling Calculation - Called on "pause", "buffering", "seeking", "viewCompleted", or "error" events
     * Calculates scaling based on stored interval data and updates accumulated metrics.
     * Clears stored interval data after successful calculation.
     *
     * @param currentPlayheadTime Current playhead time in milliseconds
     * @return ScalingResult with all calculated values, or null if calculation cannot be performed
     */
    fun calculateScalingForCurrentInterval(currentPlayheadTime: Long): ScalingCalculationUtil.ScalingResult? {
        val startTime = intervalStartTime
        val playerWidth = storedPlayerWidth
        val playerHeight = storedPlayerHeight
        val videoSourceWidth = storedVideoSourceWidth
        val videoSourceHeight = storedVideoSourceHeight

        // Validate that we have all required data for calculation
        if (startTime == null || playerWidth == null || playerHeight == null || 
            videoSourceWidth == null || videoSourceHeight == null ||
            playerWidth <= 0 || playerHeight <= 0 || videoSourceWidth <= 0 || videoSourceHeight <= 0) {
            // Clear stored data if validation fails (same as AdaptiveScalingMonitor behavior)
            clearStoredIntervalData()
            return null
        }

        val timeDelta = currentPlayheadTime - startTime
        if (timeDelta <= 0) {
            // Clear stored data if timeDelta is invalid (same as AdaptiveScalingMonitor behavior)
            clearStoredIntervalData()
            return null
        }

        // Calculate scaling metrics using stored dimensions
        val result = ScalingCalculationUtil.calculateScalingMetrics(
            playerWidth, playerHeight, videoSourceWidth, videoSourceHeight,
            currentMaxUpscale, currentMaxDownscale,
            timeDelta, totalPlaybackTime,
            totalUpscalingTimeWeighted, totalDownscalingTimeWeighted
        )

        
        // Calculate with raw pixel dimensions (no density adjustment)
        // This is what Mux might be using
        val rawPlayerWidth = (playerWidth * 3.0).toInt()  // Assuming 3x density
        val rawPlayerHeight = (playerHeight * 3.0).toInt()  // Assuming 3x density
        
        val rawWidthRatio = rawPlayerWidth.toDouble() / videoSourceWidth.toDouble()
        val rawHeightRatio = rawPlayerHeight.toDouble() / videoSourceHeight.toDouble()
        val rawMinRatio = minOf(rawWidthRatio, rawHeightRatio)
        val rawDownscale = maxOf(0.0, 1.0 - rawMinRatio)

        
        // Calculate aspect-ratio adjusted dimensions (what Mux might be using)
        val videoAspectRatio = videoSourceWidth.toDouble() / videoSourceHeight.toDouble()
        val playerAspectRatio = playerWidth.toDouble() / playerHeight.toDouble()
        
        val (effectivePlayerWidth, effectivePlayerHeight) = if (playerAspectRatio > videoAspectRatio) {
            // Player is wider - video will be letterboxed (black bars on sides)
            val effectiveHeight = playerHeight
            val effectiveWidth = (effectiveHeight * videoAspectRatio).toInt()
            Pair(effectiveWidth, effectiveHeight)
        } else {
            // Player is taller - video will be pillarboxed (black bars on top/bottom)
            val effectiveWidth = playerWidth
            val effectiveHeight = (effectiveWidth / videoAspectRatio).toInt()
            Pair(effectiveWidth, effectiveHeight)
        }
        
        // Add extension function for formatting
        fun Double.format(digits: Int): String = "%.${digits}f".format(this)

        // Update accumulated tracking variables
        currentMaxUpscale = result.maxUpscalePercentage
        currentMaxDownscale = result.maxDownscalePercentage
        totalPlaybackTime = result.totalPlaybackTime
        totalUpscalingTimeWeighted = result.totalUpscalingTimeWeighted
        totalDownscalingTimeWeighted = result.totalDownscalingTimeWeighted

        // Clear stored interval data after successful calculation (same as AdaptiveScalingMonitor)
        clearStoredIntervalData()
        return result
    }

    /**
     * Clears all stored interval data (interval time and dimensions)
     */
    private fun clearStoredIntervalData() {
        intervalStartTime = null
        storedPlayerWidth = null
        storedPlayerHeight = null
        storedVideoSourceWidth = null
        storedVideoSourceHeight = null
    }

    /**
     * Legacy method for backward compatibility
     * @deprecated Use collectDataForScaling() and calculateScalingForCurrentInterval() for proper behavior
     */
    @Deprecated("Use two-phase approach with collectDataForScaling() and calculateScalingForCurrentInterval()")
    fun processScalingCalculation(
        playerWidth: Int,
        playerHeight: Int,
        videoSourceWidth: Int,
        videoSourceHeight: Int,
        timeDelta: Long
    ): ScalingCalculationUtil.ScalingResult {

        // Single function call - calculates everything!
        val result = ScalingCalculationUtil.calculateScalingMetrics(
            playerWidth, playerHeight, videoSourceWidth, videoSourceHeight,
            currentMaxUpscale, currentMaxDownscale,
            timeDelta, totalPlaybackTime,
            totalUpscalingTimeWeighted, totalDownscalingTimeWeighted
        )

        // Update tracking variables with new values
        currentMaxUpscale = result.maxUpscalePercentage
        currentMaxDownscale = result.maxDownscalePercentage
        totalPlaybackTime = result.totalPlaybackTime
        totalUpscalingTimeWeighted = result.totalUpscalingTimeWeighted
        totalDownscalingTimeWeighted = result.totalDownscalingTimeWeighted

        return result
    }

    // Getters to access current tracking values
    fun getCurrentMaxUpscale(): Double = currentMaxUpscale
    fun getCurrentMaxDownscale(): Double = currentMaxDownscale
    fun getTotalPlaybackTime(): Long = totalPlaybackTime
    fun getTotalUpscalingTimeWeighted(): Double = totalUpscalingTimeWeighted
    fun getTotalDownscalingTimeWeighted(): Double = totalDownscalingTimeWeighted

    // Reset all tracking values (useful for new video sessions)
    fun reset() {
        currentMaxUpscale = 0.0
        currentMaxDownscale = 0.0
        totalPlaybackTime = 0L
        totalUpscalingTimeWeighted = 0.0
        totalDownscalingTimeWeighted = 0.0
        clearStoredIntervalData()
    }
}

/**
 * Extension function for formatting doubles
 */
private fun Double.format(digits: Int): String = "%.${digits}f".format(this)


