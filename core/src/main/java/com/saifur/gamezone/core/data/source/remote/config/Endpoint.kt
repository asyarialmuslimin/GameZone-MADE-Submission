package com.saifur.gamezone.core.data.source.remote.config

import com.saifur.gamezone.core.data.source.remote.response.GameDetailResponse
import com.saifur.gamezone.core.data.source.remote.response.GameListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {
    @GET("games")
    suspend fun getGames(@Query("key") key:String) : GameListResponse
    @GET("games/{id}")
    suspend fun getGameDetail(@Path("id") id:Int, @Query("key") key:String) : GameDetailResponse
    @GET("games")
    suspend fun searchGame(@Query("search") query:String, @Query("key") key:String) : GameListResponse
}