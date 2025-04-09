package com.saifur.gamezone.core.data.source.remote.config.fake

import com.saifur.gamezone.core.data.source.remote.config.Endpoint
import com.saifur.gamezone.core.data.source.remote.response.GameDetailResponse
import com.saifur.gamezone.core.data.source.remote.response.GameListResponse

class FakeEndpoint : Endpoint {
    private var dummyGameListResponse: GameListResponse = GameListResponse(results = emptyList())
    private var dummyGameDetailResponse: GameDetailResponse = GameDetailResponse(
        id = 0,
        name = "",
        description = "",
        released = "",
        backgroundImage = "",
        rating = 0.0
    )

    private var searchResponses: MutableMap<String, GameListResponse> = mutableMapOf()

    override suspend fun getGames(key: String): GameListResponse {
        return dummyGameListResponse
    }

    override suspend fun getGameDetail(id: Int, key: String): GameDetailResponse {
        return dummyGameDetailResponse.copy(id = id)
    }

    override suspend fun searchGame(query: String, key: String): GameListResponse {
        return searchResponses[query] ?: GameListResponse(results = emptyList())
    }
}
