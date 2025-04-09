package com.saifur.gamezone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameDetailResponse(

	@field:SerializedName("developers")
	val developers: List<DevelopersItem?>? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("platforms")
	val platforms: List<PlatformsItem?>? = null,

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

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("metacritic_url")
	val metacriticUrl: String? = null,

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null,

	@field:SerializedName("reddit_url")
	val redditUrl: String? = null,

	@field:SerializedName("website")
	val website: String? = null,
)

data class DevelopersItem(

	@field:SerializedName("games_count")
	val gamesCount: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("image_background")
	val imageBackground: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)