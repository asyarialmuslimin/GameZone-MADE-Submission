package com.saifur.gamezone.core.data.source.local.config.fake

import com.saifur.gamezone.core.data.source.local.config.GameDetailDao
import com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class FakeGameDetailDao : GameDetailDao {
    private val gameDetailData = mutableListOf<GameDetailEntity>()
    private val favoriteGamesFlow = MutableStateFlow<List<GameDetailEntity>>(emptyList())

    override fun getGameDetail(id: Int): Flow<GameDetailEntity?> = flow {
        emit(gameDetailData.find { it.id == id })
    }

    override fun getFavoriteGames(): Flow<List<GameDetailEntity>> = favoriteGamesFlow

    override suspend fun insertGame(game: GameDetailEntity) {
        gameDetailData.removeAll { it.id == game.id }
        gameDetailData.add(game)
        refreshFavoriteFlow()
    }

    override suspend fun updateFavoriteGame(game: GameDetailEntity) {
        val index = gameDetailData.indexOfFirst { it.id == game.id }
        if (index != -1) {
            gameDetailData[index] = game
            refreshFavoriteFlow()
        }
    }

    private fun refreshFavoriteFlow() {
        favoriteGamesFlow.value = gameDetailData.filter { it.isFavorite }
    }
}
