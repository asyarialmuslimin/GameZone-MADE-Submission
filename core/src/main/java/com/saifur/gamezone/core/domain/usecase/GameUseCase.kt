package com.saifur.gamezone.core.domain.usecase

import com.saifur.gamezone.core.domain.model.FavouriteGame
import com.saifur.gamezone.core.domain.model.Game
import com.saifur.gamezone.core.domain.model.GameDetail
import com.saifur.gamezone.core.domain.repository.IGameRepository
import com.saifur.gamezone.core.utils.Resource
import kotlinx.coroutines.flow.Flow

class GameUseCase(private val gameRepository: IGameRepository) : IGameUseCase {
    override fun getAllGames(): Flow<Resource<List<Game>>> = gameRepository.getAllGames()
    override fun getGameDetail(id: Int): Flow<Resource<GameDetail?>> = gameRepository.getGameDetail(id)
    override fun searchGame(query: String): Flow<Resource<List<Game>>> = gameRepository.searchGame(query)

    override suspend fun updateGame(game: GameDetail) = gameRepository.updateGame(game)
    override fun getFavouriteGames(): Flow<List<FavouriteGame>> = gameRepository.getFavouritedGame()
}