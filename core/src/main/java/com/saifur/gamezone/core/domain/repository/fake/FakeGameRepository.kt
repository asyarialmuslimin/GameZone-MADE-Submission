package com.saifur.gamezone.core.domain.repository.fake

import com.saifur.gamezone.core.domain.model.FavouriteGame
import com.saifur.gamezone.core.domain.model.Game
import com.saifur.gamezone.core.domain.model.GameDetail
import com.saifur.gamezone.core.domain.repository.IGameRepository
import com.saifur.gamezone.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class FakeGameRepository : IGameRepository {

    val gameList = mutableListOf<Game>()
    val gameDetails = mutableMapOf<Int, GameDetail>()
    private val favoriteGames = mutableListOf<FavouriteGame>()

    private val _favoriteGamesFlow = MutableStateFlow<List<FavouriteGame>>(emptyList())

    var shouldReturnError = false

    override fun getAllGames(): Flow<Resource<List<Game>>> = flow {
        if (shouldReturnError) {
            emit(Resource.Error("Failed to load games"))
        } else {
            emit(Resource.Success(gameList))
        }
    }

    override fun getGameDetail(id: Int): Flow<Resource<GameDetail?>> = flow {
        if (shouldReturnError) {
            emit(Resource.Error("Failed to load game detail"))
        } else {
            emit(Resource.Success(gameDetails[id]))
        }
    }

    override fun searchGame(query: String): Flow<Resource<List<Game>>> = flow {
        if (shouldReturnError) {
            emit(Resource.Error("Failed to search games"))
        } else {
            val result = gameList.filter { it.name.contains(query, ignoreCase = true) }
            emit(Resource.Success(result))
        }
    }

    override suspend fun updateGame(game: GameDetail) {
        gameDetails[game.id] = game
        if (game.isFavorite) {
            favoriteGames.removeAll { it.id == game.id }
            favoriteGames.add(FavouriteGame(game.id, game.name, game.backgroundImage, game.category, game.rating.toString()))
        } else {
            favoriteGames.removeAll { it.id == game.id }
        }
        _favoriteGamesFlow.value = favoriteGames
    }

    override fun getFavouritedGame(): Flow<List<FavouriteGame>> = _favoriteGamesFlow
}
