package com.saifur.gamezone.core.data.source.local.datasource

import com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity
import com.saifur.gamezone.core.data.source.local.entity.GameListEntity
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getAllGames(): Flow<List<GameListEntity>>
    suspend fun insertGames(games: List<GameListEntity>)
    suspend fun deleteAllGames()

    fun getGameDetail(id: Int): Flow<GameDetailEntity?>
    fun getFavoriteGames(): Flow<List<GameDetailEntity>>
    suspend fun insertGame(game: GameDetailEntity)
    suspend fun updateFavoriteGame(game: GameDetailEntity)
}