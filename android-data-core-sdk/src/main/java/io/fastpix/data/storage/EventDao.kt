package io.fastpix.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: EventEntity): Long

    @Query("SELECT * FROM analytics_events WHERE sessionId = :sessionId ORDER BY id ASC LIMIT :limit")
    suspend fun getEventsBySessionId(sessionId: String, limit: Int): List<EventEntity>

    @Query("DELETE FROM analytics_events WHERE id IN (:eventIds)")
    suspend fun deleteByIds(eventIds: List<Long>): Int

    @Query("SELECT COUNT(*) FROM analytics_events WHERE sessionId = :sessionId")
    suspend fun countBySessionId(sessionId: String): Int
}
