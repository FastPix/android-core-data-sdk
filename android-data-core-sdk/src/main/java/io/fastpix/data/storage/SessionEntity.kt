package io.fastpix.data.storage

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "analytics_sessions",
    indices = [Index(value = ["status"]), Index(value = ["viewId"])]
)
data class SessionEntity(
    @PrimaryKey val sessionId: String,
    val viewId: String,
    val status: SessionStatus,
    val createdAt: Long
)
