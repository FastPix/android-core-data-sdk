package io.fastpix.data.domain.model

data class VideoDataDetails(
    val videoId: String?,
    val videoTitle: String?,
    var videoDuration: String? = null,
    var videoThumbnail: String? = null,
    var videoSourceUrl: String? = null,
    var videoSeries: String? = null,
    var videoProducer: String? = null,
    var videoContentType: String? = null,
    var videoVariant: String? = null,
    var videoLanguage: String? = null,
    var videoDrmType: String? = null,
    var fpPlaybackId: String? = null,
    var foMediaId: String? = null,
    var fpLiveStreamId: String? = null,
)