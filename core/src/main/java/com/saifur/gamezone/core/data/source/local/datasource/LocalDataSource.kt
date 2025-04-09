package com.saifur.gamezone.core.data.source.local.datasource

import com.saifur.gamezone.core.data.source.local.config.GameDetailDao
import com.saifur.gamezone.core.data.source.local.config.GameListDao
import com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity
import com.saifur.gamezone.core.data.source.local.entity.GameListEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gameListDao: GameListDao, private val gameDetailDao: GameDetailDao) : ILocalDataSource {
    override fun getAllGames(): Flow<List<GameListEntity>> = gameListDao.getAllGames()

    override suspend fun insertGames(games: List<GameListEntity>) = gameListDao.insertGame(games)

    override suspend fun deleteAllGames() = gameListDao.deleteAllGames()

    override fun getGameDetail(id: Int): Flow<GameDetailEntity?> = gameDetailDao.getGameDetail(id)

    override fun getFavoriteGames(): Flow<List<GameDetailEntity>> = gameDetailDao.getFavoriteGames()

    override suspend fun insertGame(game: GameDetailEntity) = gameDetailDao.insertGame(game)

    override suspend fun updateFavoriteGame(game: GameDetailEntity) = gameDetailDao.updateFavoriteGame(game)
}