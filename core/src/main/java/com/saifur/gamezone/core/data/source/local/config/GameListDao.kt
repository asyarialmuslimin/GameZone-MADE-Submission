package com.saifur.gamezone.core.data.source.local.config

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saifur.gamezone.core.data.source.local.entity.GameListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameListDao {
    @Query("SELECT * FROM game_list")
    fun getAllGames(): Flow<List<GameListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(games : List<GameListEntity>)

    @Query("DELETE FROM game_list")
    suspend fun deleteAllGames()
}