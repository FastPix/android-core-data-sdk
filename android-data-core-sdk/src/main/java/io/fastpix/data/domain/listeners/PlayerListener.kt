package io.fastpix.data.domain.listeners

import io.fastpix.data.domain.model.BandwidthModel
import io.fastpix.data.domain.model.ErrorModel

interface PlayerListener {
    fun playerHeight(): Int?
    fun playerWidth(): Int?
    fun videoSourceWidth(): Int?
    fun videoSourceHeight(): Int?
    fun playHeadTime(): Int?
    fun mimeType(): String?
    fun sourceFps(): String?
    fun sourceAdvertisedBitrate(): String?
    fun sourceAdvertiseFrameRate(): String?
    fun currentPosition(): Int?
    fun sourceDuration(): Int?
    fun isPause(): Boolean?
    fun isAutoPlay(): Boolean?
    fun isBuffering(): Boolean?
    fun playerCodec(): String?
    fun sourceHostName(): String?
    fun isLive(): Boolean?
    fun sourceUrl(): String?
    fun isFullScreen(): Boolean?
    fun getBandWidthData(): BandwidthModel
    fun getPlayerError(): ErrorModel
    fun getVideoCodec(): String?
}