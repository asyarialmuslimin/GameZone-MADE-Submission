package com.saifur.gamezone.core.data.source.remote.datasource.fake

import com.saifur.gamezone.core.data.source.remote.config.ApiResponse
import com.saifur.gamezone.core.data.source.remote.datasource.IRemoteDataSource
import com.saifur.gamezone.core.data.source.remote.response.GameDetailResponse
import com.saifur.gamezone.core.data.source.remote.response.GameListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteDataSource : IRemoteDataSource {

    private var gameListResponse: GameListResponse = GameListResponse(results = emptyList())
    private var gameDetailResponse: GameDetailResponse = GameDetailResponse(
        id = 0,
        name = "",
        description = "",
        released = "",
        backgroundImage = "",
        rating = 0.0
    )
    private var searchResponses: MutableMap<String, GameListResponse> = mutableMapOf()

    override fun getAllGames(): Flow<ApiResponse<GameListResponse>> = flow {
        emit(ApiResponse.Success(gameListResponse))
    }

    override fun getGameDetail(id: Int): Flow<ApiResponse<GameDetailResponse>> = flow {
        emit(ApiResponse.Success(gameDetailResponse.copy(id = id)))
    }

    override fun searchGame(query: String): Flow<ApiResponse<GameListResponse>> = flow {
        val response = searchResponses[query]
        if (response != null) {
            emit(ApiResponse.Success(response))
        } else {
            emit(ApiResponse.Empty)
        }
    }
}
