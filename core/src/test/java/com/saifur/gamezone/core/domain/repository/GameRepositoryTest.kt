package com.saifur.gamezone.core.domain.repository

import com.saifur.gamezone.core.domain.model.Game
import com.saifur.gamezone.core.domain.model.GameDetail
import com.saifur.gamezone.core.domain.repository.fake.FakeGameRepository
import com.saifur.gamezone.core.utils.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

class GameRepositoryTest {

    private lateinit var repository: FakeGameRepository

    @Before
    fun setup() {
        repository = FakeGameRepository()
    }

    @Test
    fun `getAllGames returns success when no error`() = runTest {
        val dummyGames = listOf(
            Game(1, "Zelda", "zelda.jpg", 4.8),
            Game(2, "Mario", "mario.jpg", 4.7)
        )

        repository.apply {
            gameList.addAll(dummyGames)
            shouldReturnError = false
        }

        val result = repository.getAllGames().first()
        assertTrue(result is Resource.Success)
        assertEquals(dummyGames, (result as Resource.Success).data)
    }

    @Test
    fun `getAllGames returns error when shouldReturnError is true`() = runTest {
        repository.shouldReturnError = true
        val result = repository.getAllGames().first()
        assertTrue(result is Resource.Error)
        assertEquals("Failed to load games", (result as Resource.Error).message)
    }

    @Test
    fun `getGameDetail returns correct game when available`() = runTest {
        val detail = GameDetail(
            id = 1,
            name = "Zelda",
            backgroundImage = "zelda.jpg",
            category = "Adventure",
            rating = 4.8,
            description = "Epic game",
            releaseDate = "2022-01-01",
            lastUpdate = "2022-02-01",
            developers = "Nintendo",
            platforms = "Switch",
            website = "https://zelda.com",
            reddit = "https://reddit.com/r/zelda",
            metacritic = "https://meta.com/zelda",
            isFavorite = false
        )

        repository.gameDetails[1] = detail
        repository.shouldReturnError = false

        val result = repository.getGameDetail(1).first()
        assertTrue(result is Resource.Success)
        assertEquals(detail, (result as Resource.Success).data)
    }

    @Test
    fun `getGameDetail returns error when shouldReturnError is true`() = runTest {
        repository.shouldReturnError = true
        val result = repository.getGameDetail(1).first()
        assertTrue(result is Resource.Error)
        assertEquals("Failed to load game detail", (result as Resource.Error).message)
    }

    @Test
    fun `searchGame returns filtered results correctly`() = runTest {
        val dummyGames = listOf(
            Game(1, "Zelda", "zelda.jpg", 4.8),
            Game(2, "Mario", "mario.jpg", 4.7),
            Game(3, "Zelda 2", "zelda2.jpg", 4.5)
        )

        repository.apply {
            gameList.addAll(dummyGames)
            shouldReturnError = false
        }

        val result = repository.searchGame("zelda").first()
        assertTrue(result is Resource.Success)
        val filtered = (result as Resource.Success).data
        assertEquals(2, filtered?.size)
        assertTrue(filtered?.all { it.name.contains("zelda", ignoreCase = true) } == true)
    }

    @Test
    fun `searchGame returns error when shouldReturnError is true`() = runTest {
        repository.shouldReturnError = true
        val result = repository.searchGame("zelda").first()
        assertTrue(result is Resource.Error)
        assertEquals("Failed to search games", (result as Resource.Error).message)
    }

    @Test
    fun `updateGame adds to favorites when isFavorite is true`() = runTest {
        val detail = GameDetail(
            id = 1,
            name = "Zelda",
            backgroundImage = "zelda.jpg",
            category = "Adventure",
            rating = 4.8,
            description = "",
            releaseDate = "",
            lastUpdate = "",
            developers = "",
            platforms = "",
            website = "",
            reddit = "",
            metacritic = "",
            isFavorite = true
        )

        repository.updateGame(detail)

        val favorites = repository.getFavouritedGame().first()
        assertEquals(1, favorites.size)
        assertEquals(detail.id, favorites.first().id)
    }

    @Test
    fun `updateGame removes from favorites when isFavorite is false`() = runTest {
        val detail = GameDetail(
            id = 2,
            name = "Mario",
            backgroundImage = "mario.jpg",
            category = "Platform",
            rating = 4.7,
            description = "",
            releaseDate = "",
            lastUpdate = "",
            developers = "",
            platforms = "",
            website = "",
            reddit = "",
            metacritic = "",
            isFavorite = true
        )

        repository.updateGame(detail)
        val favoritesBefore = repository.getFavouritedGame().first()
        assertEquals(1, favoritesBefore.size)

        repository.updateGame(detail.copy(isFavorite = false))
        val favoritesAfter = repository.getFavouritedGame().first()
        assertTrue(favoritesAfter.isEmpty())
    }
}