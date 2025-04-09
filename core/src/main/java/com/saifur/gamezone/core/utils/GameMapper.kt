package com.saifur.gamezone.core.utils

import com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity
import com.saifur.gamezone.core.data.source.local.entity.GameListEntity
import com.saifur.gamezone.core.data.source.remote.response.GameDetailResponse
import com.saifur.gamezone.core.data.source.remote.response.ResultsItem
import com.saifur.gamezone.core.domain.model.FavouriteGame
import com.saifur.gamezone.core.domain.model.Game
import com.saifur.gamezone.core.domain.model.GameDetail

object GameMapper {
    fun entityToModel(entity: GameListEntity): Game = Game(
        id = entity.gameId,
        name = entity.name,
        backgroundImage = entity.backgroundImage,
        rating = entity.rating
    )

    fun responseToModel(response:ResultsItem): Game = Game(
        id = response.id ?: 0,
        name = response.name ?: "",
        backgroundImage = response.backgroundImage ?: "",
        rating = response.rating ?: 0.0
    )

    fun responseToEntity(response: ResultsItem): GameListEntity = GameListEntity(
        gameId = response.id ?: 0,
        name = response.name ?: "",
        backgroundImage = response.backgroundImage ?: "",
        rating = response.rating ?: 0.0
    )

    fun entityDetailToModel(entity: GameDetailEntity?): GameDetail? {
        if (entity != null) {
            return GameDetail(
                id = entity.id,
                name = entity.name,
                backgroundImage = entity.backgroundImage,
                category = entity.category,
                rating = entity.rating,
                description = entity.description,
                releaseDate = entity.releaseDate,
                lastUpdate = entity.lastUpdate,
                developers = entity.developers,
                platforms = entity.platforms,
                website = entity.website,
                reddit = entity.reddit,
                metacritic = entity.metacritic,
                isFavorite = entity.isFavorite
            )
        } else {
            return null
        }
    }

    fun responseDetailToEntity(response: GameDetailResponse): GameDetailEntity = GameDetailEntity(
        id = response.id ?: 0,
        name = response.name ?: "",
        backgroundImage = response.backgroundImage ?: "",
        category = response.genres?.first()?.name ?: "",
        rating = response.rating ?: 0.0,
        description = response.description ?: "",
        releaseDate = response.released ?: "",
        lastUpdate = response.updated ?: "",
        developers = response.developers?.joinToString(", ") { it?.name.toString() } ?: "",
        platforms = response.platforms?.joinToString(", ") { it?.platform?.name.toString() } ?: "",
        website = response.website ?: "",
        reddit = response.redditUrl ?: "",
        metacritic = response.metacriticUrl ?: "",
        isFavorite = false
    )

    fun modelDetailToEntity(model: GameDetail): GameDetailEntity = GameDetailEntity(
        id = model.id,
        name = model.name,
        backgroundImage = model.backgroundImage,
        category = model.category,
        rating = model.rating,
        description = model.description,
        releaseDate = model.releaseDate,
        lastUpdate = model.lastUpdate,
        developers = model.developers,
        platforms = model.platforms,
        website = model.website,
        reddit = model.reddit,
        metacritic = model.metacritic,
        isFavorite = model.isFavorite
    )

    fun entityDetailToFavouriteListModel(entity: GameDetailEntity): FavouriteGame = FavouriteGame(
        id = entity.id,
        name = entity.name,
        backgroundImage = entity.backgroundImage,
        category = entity.category,
        rating = entity.rating.toString()
    )
}