package com.saifur.gamezone.core.data.source.local.config

import com.saifur.gamezone.core.data.source.local.config.fake.FakeGameListDao
import com.saifur.gamezone.core.data.source.local.entity.GameListEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GameListDaoTest {

    private lateinit var dao: FakeGameListDao

    private val game1 = GameListEntity(
        gameId = 1,
        name = "Game One",
        backgroundImage = "image1.jpg",
        rating = 4.5
    )

    private val game2 = GameListEntity(
        gameId = 2,
        name = "Game Two",
        backgroundImage = "image2.jpg",
        rating = 4.0
    )

    @Before
    fun setup() {
        dao = FakeGameListDao()
    }

    @Test
    fun `getAllGames should return empty list initially`() = runTest {
        val result = dao.getAllGames().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `insertGame should update the game list`() = runTest {
        dao.insertGame(listOf(game1, game2))

        val result = dao.getAllGames().first()
        assertEquals(2, result.size)
        assertEquals("Game One", result[0].name)
        assertEquals("Game Two", result[1].name)
    }

    @Test
    fun `deleteAllGames should clear the list`() = runTest {
        dao.insertGame(listOf(game1, game2))
        dao.deleteAllGames()

        val result = dao.getAllGames().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `insertGame should replace existing data`() = runTest {
        dao.insertGame(listOf(game1))
        dao.insertGame(listOf(game2))

        val result = dao.getAllGames().first()
        assertEquals(1, result.size)
        assertEquals("Game Two", result[0].name)
    }
}