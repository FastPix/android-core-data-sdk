package io.fastpix.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [SessionEntity::class, EventEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(SessionStatusConverter::class)
abstract class AnalyticsDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun eventDao(): EventDao
}

class SessionStatusConverter {
    @TypeConverter
    fun fromSessionStatus(value: SessionStatus): String = value.name

    @TypeConverter
    fun toSessionStatus(value: String): SessionStatus = SessionStatus.valueOf(value)
}
