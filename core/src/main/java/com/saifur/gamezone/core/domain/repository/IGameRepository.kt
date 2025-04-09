package com.saifur.gamezone.core.domain.repository

import com.saifur.gamezone.core.domain.model.FavouriteGame
import com.saifur.gamezone.core.utils.Resource
import com.saifur.gamezone.core.domain.model.Game
import com.saifur.gamezone.core.domain.model.GameDetail
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    fun getAllGames() : Flow<Resource<List<Game>>>
    fun getGameDetail(id:Int) : Flow<Resource<GameDetail?>>
    fun searchGame(query:String) : Flow<Resource<List<Game>>>
    suspend fun updateGame(game:GameDetail)
    fun getFavouritedGame() : Flow<List<FavouriteGame>>
}