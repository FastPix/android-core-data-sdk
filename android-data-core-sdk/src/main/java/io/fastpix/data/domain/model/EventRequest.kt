package io.fastpix.data.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventRequest(
    @SerialName("metadata")
    val metadata: Metadata,
    @SerialName("events")
    val events: List<Map<String, String?>>
)

@Serializable
data class Metadata(
    @SerialName("transmission_timestamp")
    val transmission_timestamp: String
)
