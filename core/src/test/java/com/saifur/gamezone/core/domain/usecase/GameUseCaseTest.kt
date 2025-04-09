package com.saifur.gamezone.core.domain.usecase

import com.saifur.gamezone.core.domain.model.Game
import com.saifur.gamezone.core.domain.model.GameDetail
import com.saifur.gamezone.core.domain.usecase.fake.FakeGameUseCase
import com.saifur.gamezone.core.utils.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

class GameUseCaseTest {

    private lateinit var useCase: FakeGameUseCase

    @Before
    fun setUp() {
        useCase = FakeGameUseCase()
    }

    @Test
    fun `getAllGames returns success when no error`() = runTest {
        val dummyGames = listOf(
            Game(1, "Zelda", "zelda.jpg", 4.8),
            Game(2, "Mario", "mario.jpg", 4.7)
        )

        useCase.games.addAll(dummyGames)
        useCase.shouldReturnError = false

        val result = useCase.getAllGames().first()
        assertTrue(result is Resource.Success)
        assertEquals(dummyGames, (result as Resource.Success).data)
    }

    @Test
    fun `getAllGames returns error when shouldReturnError is true`() = runTest {
        useCase.shouldReturnError = true
        val result = useCase.getAllGames().first()
        assertTrue(result is Resource.Error)
        assertEquals("Unable to fetch games", (result as Resource.Error).message)
    }

    @Test
    fun `getGameDetail returns correct data`() = runTest {
        val detail = GameDetail(
            id = 1,
            name = "Zelda",
            backgroundImage = "zelda.jpg",
            category = "Adventure",
            rating = 4.8,
            description = "Great game",
            releaseDate = "",
            lastUpdate = "",
            developers = "",
            platforms = "",
            website = "",
            reddit = "",
            metacritic = "",
            isFavorite = false
        )

        useCase.gameDetails[1] = detail
        useCase.shouldReturnError = false

        val result = useCase.getGameDetail(1).first()
        assertTrue(result is Resource.Success)
        assertEquals(detail, (result as Resource.Success).data)
    }

    @Test
    fun `getGameDetail returns error when shouldReturnError is true`() = runTest {
        useCase.shouldReturnError = true
        val result = useCase.getGameDetail(1).first()
        assertTrue(result is Resource.Error)
        assertEquals("Unable to fetch game detail", (result as Resource.Error).message)
    }

    @Test
    fun `searchGame returns filtered list when successful`() = runTest {
        useCase.games.addAll(
            listOf(
                Game(1, "Zelda", "zelda.jpg", 4.8),
                Game(2, "Zelda 2", "zelda2.jpg", 4.6),
                Game(3, "Mario", "mario.jpg", 4.7)
            )
        )
        useCase.shouldReturnError = false

        val result = useCase.searchGame("zelda").first()
        assertTrue(result is Resource.Success)
        val filtered = (result as Resource.Success).data
        assertEquals(2, filtered?.size)
        assertTrue(filtered?.all { it.name.contains("zelda", ignoreCase = true) } == true)
    }

    @Test
    fun `searchGame returns error when shouldReturnError is true`() = runTest {
        useCase.shouldReturnError = true
        val result = useCase.searchGame("zelda").first()
        assertTrue(result is Resource.Error)
        assertEquals("Search failed", (result as Resource.Error).message)
    }

    @Test
    fun `updateGame adds game to favorites if isFavorite is true`() = runTest {
        val game = GameDetail(
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

        useCase.updateGame(game)

        val favorites = useCase.getFavouriteGames().first()
        assertEquals(1, favorites.size)
        assertEquals(game.id, favorites.first().id)
    }

    @Test
    fun `updateGame removes game from favorites if isFavorite is false`() = runTest {
        val game = GameDetail(
            id = 2,
            name = "Mario",
            backgroundImage = "mario.jpg",
            category = "Platform",
            rating = 4.5,
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

        useCase.updateGame(game)

        // now set isFavorite to false
        useCase.updateGame(game.copy(isFavorite = false))

        val favorites = useCase.getFavouriteGames().first()
        assertTrue(favorites.none { it.id == game.id })
    }
}