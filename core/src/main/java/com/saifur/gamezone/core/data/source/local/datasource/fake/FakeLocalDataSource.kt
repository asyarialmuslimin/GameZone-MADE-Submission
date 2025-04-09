package com.saifur.gamezone.core.data.source.local.datasource.fake

import com.saifur.gamezone.core.data.source.local.datasource.ILocalDataSource
import com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity
import com.saifur.gamezone.core.data.source.local.entity.GameListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource : ILocalDataSource {
    private val gameListData = mutableListOf<GameListEntity>()
    private val gameDetailData = mutableListOf<GameDetailEntity>()

    private val _gameListFlow = MutableStateFlow<List<GameListEntity>>(emptyList())
    private val _favoriteGamesFlow = MutableStateFlow<List<GameDetailEntity>>(emptyList())

    override fun getAllGames(): Flow<List<GameListEntity>> = _gameListFlow

    override suspend fun insertGames(games: List<GameListEntity>) {
        gameListData.clear()
        gameListData.addAll(games)
        _gameListFlow.value = gameListData.toList()
    }

    override suspend fun deleteAllGames() {
        gameListData.clear()
        _gameListFlow.value = emptyList()
    }

    override fun getGameDetail(id: Int): Flow<GameDetailEntity?> = flow {
        emit(gameDetailData.find { it.id == id })
    }

    override fun getFavoriteGames(): Flow<List<GameDetailEntity>> = _favoriteGamesFlow

    override suspend fun insertGame(game: GameDetailEntity) {
        gameDetailData.removeAll { it.id == game.id }
        gameDetailData.add(game)
        updateFavoriteFlow()
    }

    override suspend fun updateFavoriteGame(game: GameDetailEntity) {
        val index = gameDetailData.indexOfFirst { it.id == game.id }
        if (index != -1) {
            gameDetailData[index] = game
            updateFavoriteFlow()
        }
    }

    private fun updateFavoriteFlow() {
        _favoriteGamesFlow.value = gameDetailData.filter { it.isFavorite }
    }
}
