package com.saifur.gamezone.core.data.source.local.config

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDetailDao {
    @Query("SELECT * FROM game_detail WHERE id = :id")
    fun getGameDetail(id: Int): Flow<GameDetailEntity?>
    @Query("SELECT * FROM game_detail WHERE is_favorite = 1")
    fun getFavoriteGames(): Flow<List<GameDetailEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameDetailEntity)
    @Update
    suspend fun updateFavoriteGame(game: GameDetailEntity)
}