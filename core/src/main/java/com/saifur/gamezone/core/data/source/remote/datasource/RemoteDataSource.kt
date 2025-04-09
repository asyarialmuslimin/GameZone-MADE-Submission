package com.saifur.gamezone.core.data.source.remote.datasource

import com.saifur.gamezone.core.BuildConfig
import com.saifur.gamezone.core.data.source.remote.config.ApiResponse
import com.saifur.gamezone.core.data.source.remote.config.Endpoint
import com.saifur.gamezone.core.data.source.remote.response.GameDetailResponse
import com.saifur.gamezone.core.data.source.remote.response.GameListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val endpoint : Endpoint) : IRemoteDataSource {
    private val apiKey = BuildConfig.API_KEY;
    override fun getAllGames() : Flow<ApiResponse<GameListResponse>> = flow {
        try {
            val response = endpoint.getGames(apiKey)
            if (response.results.isNotEmpty()) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e:Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getGameDetail(id: Int): Flow<ApiResponse<GameDetailResponse>> = flow {
        try {
            val response = endpoint.getGameDetail(id, apiKey)
            emit(ApiResponse.Success(response))
        } catch (e:Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchGame(query: String): Flow<ApiResponse<GameListResponse>> = flow {
        try {
            val response = endpoint.searchGame(query, apiKey)
            if (response.results.isNotEmpty()) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e:Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}