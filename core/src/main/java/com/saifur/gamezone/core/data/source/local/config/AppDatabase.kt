package com.saifur.gamezone.core.data.source.local.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saifur.gamezone.core.BuildConfig
import com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity
import com.saifur.gamezone.core.data.source.local.entity.GameListEntity
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [GameListEntity::class, GameDetailEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameListDao(): GameListDao
    abstract fun gameDetailDao(): GameDetailDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            val passPhase = BuildConfig.PASS_KEY.toByteArray(Charsets.UTF_8)
            val factory = SupportFactory(passPhase)
            return synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gamezonedb"
                )
                    .openHelperFactory(factory)
                    .build()
                instance
            }
        }
    }
}