package io.fastpix.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(session: SessionEntity)

    @Query("SELECT * FROM analytics_sessions WHERE status = :status ORDER BY createdAt ASC")
    suspend fun getSessionsByStatus(status: SessionStatus): List<SessionEntity>

    @Query("SELECT * FROM analytics_sessions WHERE status = :status AND viewId = :viewId LIMIT 1")
    suspend fun getSessionByStatusAndViewId(status: SessionStatus, viewId: String): SessionEntity?

    @Query("UPDATE analytics_sessions SET status = :status WHERE status = :fromStatus")
    suspend fun updateStatus(fromStatus: SessionStatus, status: SessionStatus): Int

    @Query("UPDATE analytics_sessions SET status = :status WHERE sessionId = :sessionId")
    suspend fun updateStatusBySessionId(sessionId: String, status: SessionStatus): Int

    @Query("SELECT * FROM analytics_sessions ORDER BY createdAt ASC")
    suspend fun getAllSessions(): List<SessionEntity>

    @Query("DELETE FROM analytics_sessions WHERE sessionId = :sessionId")
    suspend fun deleteBySessionId(sessionId: String): Int
}
