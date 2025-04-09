package com.saifur.gamezone.core.utils

import com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity
import com.saifur.gamezone.core.data.source.local.entity.GameListEntity
import com.saifur.gamezone.core.data.source.remote.response.DevelopersItem
import com.saifur.gamezone.core.data.source.remote.response.GameDetailResponse
import com.saifur.gamezone.core.data.source.remote.response.GenresItem
import com.saifur.gamezone.core.data.source.remote.response.Platform
import com.saifur.gamezone.core.data.source.remote.response.PlatformsItem
import com.saifur.gamezone.core.data.source.remote.response.ResultsItem
import com.saifur.gamezone.core.domain.model.GameDetail
import org.junit.Assert.*
import org.junit.Test

class GameMapperTest {

    @Test
    fun `entityToModel should map GameListEntity to Game`() {
        val entity = GameListEntity(1, "Elden Ring", "image.jpg", 4.9)
        val result = GameMapper.entityToModel(entity)

        assertEquals(1, result.id)
        assertEquals("Elden Ring", result.name)
        assertEquals("image.jpg", result.backgroundImage)
        assertEquals(4.9, result.rating, 0.001)
    }

    @Test
    fun `responseToModel should map ResultsItem to Game`() {
        val response = ResultsItem(id = 2, name = "Hades", backgroundImage = "bg.png", rating = 4.5)
        val result = GameMapper.responseToModel(response)

        assertEquals(2, result.id)
        assertEquals("Hades", result.name)
        assertEquals("bg.png", result.backgroundImage)
        assertEquals(4.5, result.rating, 0.001)
    }

    @Test
    fun `responseToEntity should map ResultsItem to GameListEntity`() {
        val response =
            ResultsItem(id = 3, name = "Celeste", backgroundImage = "celeste.jpg", rating = 4.7)
        val result = GameMapper.responseToEntity(response)

        assertEquals(3, result.gameId)
        assertEquals("Celeste", result.name)
        assertEquals("celeste.jpg", result.backgroundImage)
        assertEquals(4.7, result.rating, 0.001)
    }

    @Test
    fun `entityDetailToModel should map GameDetailEntity to GameDetail`() {
        val entity = GameDetailEntity(
            id = 4,
            name = "The Witcher 3",
            backgroundImage = "witcher.jpg",
            category = "RPG",
            rating = 5.0,
            description = "Great game",
            releaseDate = "2015-05-19",
            lastUpdate = "2023-01-01",
            developers = "CDPR",
            platforms = "PC, PS4",
            website = "http://witcher.com",
            reddit = "http://reddit.com/witcher",
            metacritic = "http://metacritic.com/witcher",
            isFavorite = true
        )

        val result = GameMapper.entityDetailToModel(entity)
        assertNotNull(result)
        assertEquals("The Witcher 3", result?.name)
        assertTrue(result?.isFavorite == true)
    }

    @Test
    fun `entityDetailToModel should return null when input is null`() {
        val result = GameMapper.entityDetailToModel(null)
        assertNull(result)
    }

    @Test
    fun `responseDetailToEntity should map GameDetailResponse to GameDetailEntity`() {
        val response = GameDetailResponse(
            id = 5,
            name = "Hollow Knight",
            backgroundImage = "hk.jpg",
            genres = listOf(GenresItem(name = "Metroidvania")),
            rating = 4.8,
            description = "A deep and challenging game",
            released = "2017-02-24",
            updated = "2023-01-01",
            developers = listOf(DevelopersItem(name = "Team Cherry")),
            platforms = listOf(PlatformsItem(platform = Platform(name = "PC"))),
            website = "http://hollowknight.com",
            redditUrl = "http://reddit.com/hollowknight",
            metacriticUrl = "http://metacritic.com/hollowknight"
        )

        val result = GameMapper.responseDetailToEntity(response)

        assertEquals(5, result.id)
        assertEquals("Metroidvania", result.category)
        assertEquals("Team Cherry", result.developers)
        assertEquals("PC", result.platforms)
        assertFalse(result.isFavorite)
    }

    @Test
    fun `modelDetailToEntity should map GameDetail to GameDetailEntity`() {
        val model = GameDetail(
            id = 6,
            name = "Stardew Valley",
            backgroundImage = "sdv.jpg",
            category = "Simulation",
            rating = 4.9,
            description = "Farm life",
            releaseDate = "2016-02-26",
            lastUpdate = "2023-01-01",
            developers = "ConcernedApe",
            platforms = "PC, Switch",
            website = "http://stardewvalley.net",
            reddit = "http://reddit.com/sdv",
            metacritic = "http://metacritic.com/sdv",
            isFavorite = true
        )

        val result = GameMapper.modelDetailToEntity(model)
        assertEquals("Stardew Valley", result.name)
        assertTrue(result.isFavorite)
    }

    @Test
    fun `entityDetailToFavouriteListModel should map GameDetailEntity to FavouriteGame`() {
        val entity = GameDetailEntity(
            id = 7, name = "Slay the Spire", backgroundImage = "sts.jpg", category = "Card",
            rating = 4.5, description = "", releaseDate = "", lastUpdate = "", developers = "",
            platforms = "", website = "", reddit = "", metacritic = "", isFavorite = true
        )

        val result = GameMapper.entityDetailToFavouriteListModel(entity)
        assertEquals("Slay the Spire", result.name)
        assertEquals("4.5", result.rating)
    }
}
