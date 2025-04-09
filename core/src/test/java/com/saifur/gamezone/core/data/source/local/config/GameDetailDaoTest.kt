package com.saifur.gamezone.core.data.source.local.config

import com.saifur.gamezone.core.data.source.local.config.fake.FakeGameDetailDao
import com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameDetailDaoTest {

    private lateinit var dao: FakeGameDetailDao

    private val game1 = GameDetailEntity(
        id = 1,
        name = "Game One",
        backgroundImage = "image1.jpg",
        category = "RPG",
        rating = 4.5,
        description = "An epic RPG game",
        releaseDate = "2022-01-01",
        lastUpdate = "2023-01-01",
        developers = "Dev A",
        platforms = "PC",
        website = "https://game1.com",
        reddit = "https://reddit.com/game1",
        metacritic = "https://metacritic.com/game1",
        isFavorite = false
    )

    private val game2 = game1.copy(id = 2, name = "Game Two", isFavorite = true)

    @Before
    fun setup() {
        dao = FakeGameDetailDao()
    }

    @Test
    fun `insertGame should add game correctly`() = runTest {
        dao.insertGame(game1)
        val result = dao.getGameDetail(1).first()
        assertNotNull(result)
        assertEquals("Game One", result?.name)
    }

    @Test
    fun `getGameDetail should return null for non-existent ID`() = runTest {
        val result = dao.getGameDetail(99).first()
        assertNull(result)
    }

    @Test
    fun `getFavoriteGames should return only favorite games`() = runTest {
        dao.insertGame(game1)
        dao.insertGame(game2)

        val favorites = dao.getFavoriteGames().first()
        assertEquals(1, favorites.size)
        assertEquals("Game Two", favorites[0].name)
    }

    @Test
    fun `updateFavoriteGame should update favorite status correctly`() = runTest {
        dao.insertGame(game1)
        val updated = game1.copy(isFavorite = true)
        dao.updateFavoriteGame(updated)

        val updatedResult = dao.getGameDetail(1).first()
        assertTrue(updatedResult?.isFavorite == true)

        val favorites = dao.getFavoriteGames().first()
        assertEquals(1, favorites.size)
        assertEquals("Game One", favorites[0].name)
    }

    @Test
    fun `updateFavoriteGame should not crash when game does not exist`() = runTest {
        val notInsertedGame = game1.copy(id = 99, isFavorite = true)
        dao.updateFavoriteGame(notInsertedGame)

        val result = dao.getGameDetail(99).first()
        assertNull(result)
    }
}
