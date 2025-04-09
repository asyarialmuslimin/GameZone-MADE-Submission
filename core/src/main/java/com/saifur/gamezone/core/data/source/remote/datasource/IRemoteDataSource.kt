package com.saifur.gamezone.core.data.source.remote.datasource

import com.saifur.gamezone.core.data.source.remote.config.ApiResponse
import com.saifur.gamezone.core.data.source.remote.response.GameDetailResponse
import com.saifur.gamezone.core.data.source.remote.response.GameListResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    fun getAllGames() : Flow<ApiResponse<GameListResponse>>
    fun getGameDetail(id:Int) : Flow<ApiResponse<GameDetailResponse>>
    fun searchGame(query:String) : Flow<ApiResponse<GameListResponse>>
}