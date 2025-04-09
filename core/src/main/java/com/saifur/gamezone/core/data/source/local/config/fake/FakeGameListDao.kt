package com.saifur.gamezone.core.data.source.local.config.fake

import com.saifur.gamezone.core.data.source.local.config.GameListDao
import com.saifur.gamezone.core.data.source.local.entity.GameListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeGameListDao : GameListDao {

    private val gameListData = mutableListOf<GameListEntity>()
    private val gameListFlow = MutableStateFlow<List<GameListEntity>>(emptyList())

    override fun getAllGames(): Flow<List<GameListEntity>> = gameListFlow

    override suspend fun insertGame(games: List<GameListEntity>) {
        gameListData.clear()
        gameListData.addAll(games)
        gameListFlow.value = gameListData.toList()
    }

    override suspend fun deleteAllGames() {
        gameListData.clear()
        gameListFlow.value = emptyList()
    }
}
