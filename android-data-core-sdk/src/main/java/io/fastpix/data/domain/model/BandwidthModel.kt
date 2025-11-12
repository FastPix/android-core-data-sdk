package io.fastpix.data.domain.model

data class BandwidthModel(
    var requestId: String? = null,
    var requestUrl: String? = null,
    var requestMethod: String? = null,
    var requestResponseCode: String? = null,
    var requestResponseTime: String? = null,
    var requestResponseSize: String? = null,
    var requestResponseHeaders: Map<String, String>? = null,
    var requestResponseBody: String? = null,
    var requestErrorCode: String? = null,
    var requestHostName: String? = null,
    var requestCancel: String? = null,
    var requestErrorText: String? = null,
)