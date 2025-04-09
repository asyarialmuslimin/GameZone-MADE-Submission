package com.saifur.gamezone.core.domain.usecase.fake

import com.saifur.gamezone.core.domain.model.FavouriteGame
import com.saifur.gamezone.core.domain.model.Game
import com.saifur.gamezone.core.domain.model.GameDetail
import com.saifur.gamezone.core.domain.usecase.IGameUseCase
import com.saifur.gamezone.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class FakeGameUseCase : IGameUseCase {

    val games = mutableListOf<Game>()
    val gameDetails = mutableMapOf<Int, GameDetail>()
    private val favouriteGames = mutableListOf<FavouriteGame>()

    private val _favouriteGamesFlow = MutableStateFlow<List<FavouriteGame>>(emptyList())

    var shouldReturnError = false

    override fun getAllGames(): Flow<Resource<List<Game>>> = flow {
        if (shouldReturnError) {
            emit(Resource.Error("Unable to fetch games"))
        } else {
            emit(Resource.Success(games))
        }
    }

    override fun getGameDetail(id: Int): Flow<Resource<GameDetail?>> = flow {
        if (shouldReturnError) {
            emit(Resource.Error("Unable to fetch game detail"))
        } else {
            emit(Resource.Success(gameDetails[id]))
        }
    }

    override fun searchGame(query: String): Flow<Resource<List<Game>>> = flow {
        if (shouldReturnError) {
            emit(Resource.Error("Search failed"))
        } else {
            val result = games.filter { it.name.contains(query, ignoreCase = true) }
            emit(Resource.Success(result))
        }
    }

    override suspend fun updateGame(game: GameDetail) {
        gameDetails[game.id] = game
        if (game.isFavorite) {
            favouriteGames.removeAll { it.id == game.id }
            favouriteGames.add(FavouriteGame(game.id, game.name, game.backgroundImage, game.category, game.rating.toString()))
        } else {
            favouriteGames.removeAll { it.id == game.id }
        }
        _favouriteGamesFlow.value = favouriteGames
    }

    override fun getFavouriteGames(): Flow<List<FavouriteGame>> = _favouriteGamesFlow
}
