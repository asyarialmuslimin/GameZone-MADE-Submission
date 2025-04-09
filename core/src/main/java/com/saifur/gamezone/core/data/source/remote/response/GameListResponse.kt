package com.saifur.gamezone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameListResponse(
    @field:SerializedName("results")
    val results: List<ResultsItem>
)

data class PlatformsItem(

    @field:SerializedName("platform")
    val platform: Platform? = null
)

data class Platform(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("slug")
    val slug: String? = null
)

data class ResultsItem(
    @field:SerializedName("rating")
    val rating: Double? = null,

    @field:SerializedName("metacritic")
    val metacritic: Int? = null,

    @field:SerializedName("platforms")
    val platforms: List<PlatformsItem?>? = null,

    @field:SerializedName("genres")
    val genres: List<GenresItem?>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("released")
    val released: String? = null,

    @field:SerializedName("background_image")
    val backgroundImage: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("updated")
    val updated: String? = null,
)

data class GenresItem(
    @field:SerializedName("name")
    val name: String? = null,
)