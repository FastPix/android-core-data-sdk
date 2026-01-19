package io.fastpix.data.utils

import io.fastpix.data.domain.model.events.BaseEvent
import io.fastpix.data.domain.model.events.BufferedEvent
import io.fastpix.data.domain.model.events.BufferingEvent
import io.fastpix.data.domain.model.events.EndedEvent
import io.fastpix.data.domain.model.events.ErrorEvent
import io.fastpix.data.domain.model.events.PauseEvent
import io.fastpix.data.domain.model.events.PlayEvent
import io.fastpix.data.domain.model.events.PlayerReadyEvent
import io.fastpix.data.domain.model.events.PlayingEvent
import io.fastpix.data.domain.model.events.PulseEvent
import io.fastpix.data.domain.model.events.RequestCancelledEvent
import io.fastpix.data.domain.model.events.RequestCompletedEvent
import io.fastpix.data.domain.model.events.RequestFailedEvent
import io.fastpix.data.domain.model.events.SeekedEvent
import io.fastpix.data.domain.model.events.SeekingEvent
import io.fastpix.data.domain.model.events.VariantChangedEvent
import io.fastpix.data.domain.model.events.ViewBeginEvent
import io.fastpix.data.domain.model.events.ViewCompletedEvent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/**
 * Shared Json instance for serialization/deserialization
 * Configured with encodeDefaults = false and explicitNulls = false
 * to match backend contract requirements
 * Includes polymorphic serialization configuration for all BaseEvent subtypes
 */
object JsonSerializer {
    private val eventSerializersModule = SerializersModule {
        polymorphic(BaseEvent::class) {
            subclass(PlayEvent::class)
            subclass(PlayingEvent::class)
            subclass(PauseEvent::class)
            subclass(EndedEvent::class)
            subclass(PulseEvent::class)
            subclass(SeekedEvent::class)
            subclass(SeekingEvent::class)
            subclass(BufferedEvent::class)
            subclass(BufferingEvent::class)
            subclass(ErrorEvent::class)
            subclass(PlayerReadyEvent::class)
            subclass(ViewBeginEvent::class)
            subclass(ViewCompletedEvent::class)
            subclass(VariantChangedEvent::class)
            subclass(RequestCompletedEvent::class)
            subclass(RequestFailedEvent::class)
            subclass(RequestCancelledEvent::class)
        }
    }

    val json: Json = Json {
        encodeDefaults = false
        explicitNulls = false
        ignoreUnknownKeys = true
        serializersModule = eventSerializersModule
    }
}
