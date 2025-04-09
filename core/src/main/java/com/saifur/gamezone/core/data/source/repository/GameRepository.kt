package com.saifur.gamezone.core.data.source.repository

import com.saifur.gamezone.core.data.source.local.datasource.ILocalDataSource
import com.saifur.gamezone.core.data.source.remote.config.ApiResponse
import com.saifur.gamezone.core.utils.Resource
import com.saifur.gamezone.core.data.source.remote.datasource.IRemoteDataSource
import com.saifur.gamezone.core.domain.model.FavouriteGame
import com.saifur.gamezone.core.utils.networkBoundResource
import com.saifur.gamezone.core.domain.model.Game
import com.saifur.gamezone.core.domain.model.GameDetail
import com.saifur.gamezone.core.domain.repository.IGameRepository
import com.saifur.gamezone.core.utils.GameMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : IGameRepository {
    override fun getAllGames(): Flow<Resource<List<Game>>> = networkBoundResource(
        query = {
            localDataSource.getAllGames()
                .map { entities -> entities.map { GameMapper.entityToModel(it) } }
        },
        fetch = { remoteDataSource.getAllGames() },
        saveFetchResult = { games ->
            localDataSource.deleteAllGames()
            val gameEntities = games.results.map { game ->
                GameMapper.responseToEntity(game)
            }
            localDataSource.insertGames(gameEntities)
        },
        shouldFetch = { it.isEmpty() }
    )

    override fun getGameDetail(id:Int): Flow<Resource<GameDetail?>> = networkBoundResource(
        query = {
            val result = localDataSource.getGameDetail(id)
            result.map { entity ->
                GameMapper.entityDetailToModel(entity)
            }
        },
        fetch = { remoteDataSource.getGameDetail(id) },
        saveFetchResult = { game ->
            localDataSource.insertGame(GameMapper.responseDetailToEntity(game))
            },
        shouldFetch = { it == null }
    )

    override fun searchGame(query: String): Flow<Resource<List<Game>>> {
        return remoteDataSource.searchGame(query).map { response ->
            when(response) {
                is ApiResponse.Empty -> {
                    Resource.Success(emptyList())
                }
                is ApiResponse.Error -> {
                    Resource.Error(response.errorMessage, null)
                }
                is ApiResponse.Success -> {
                    val games = response.data.results.map { game ->
                        GameMapper.responseToModel(game)
                    }
                    Resource.Success(games)
                }
            }
        }
    }

    override suspend fun updateGame(game: GameDetail) {
        localDataSource.updateFavoriteGame(GameMapper.modelDetailToEntity(game))
    }

    override fun getFavouritedGame(): Flow<List<FavouriteGame>> {
        return localDataSource.getFavoriteGames()
            .map { games ->
                games.map { game ->
                    GameMapper.entityDetailToFavouriteListModel(game)
                }
            }
    }
}