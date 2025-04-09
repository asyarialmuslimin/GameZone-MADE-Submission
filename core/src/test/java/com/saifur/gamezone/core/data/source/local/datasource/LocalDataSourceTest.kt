package com.saifur.gamezone.core.data.source.local.datasource

import com.saifur.gamezone.core.data.source.local.datasource.fake.FakeLocalDataSource
import com.saifur.gamezone.core.data.source.local.entity.GameDetailEntity
import com.saifur.gamezone.core.data.source.local.entity.GameListEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

class LocalDataSourceTest {

    private lateinit var dataSource: FakeLocalDataSource

    private val gameList1 = GameListEntity(
        gameId = 1,
        name = "Game One",
        backgroundImage = "image1.jpg",
        rating = 4.5
    )

    private val gameList2 = GameListEntity(
        gameId = 2,
        name = "Game Two",
        backgroundImage = "image2.jpg",
        rating = 4.2
    )

    private val detail1 = GameDetailEntity(
        id = 1,
        name = "Game Detail One",
        backgroundImage = "bg1",
        category = "RPG",
        rating = 4.5,
        description = "Desc 1",
        releaseDate = "2022-01-01",
        lastUpdate = "2022-02-02",
        developers = "Dev 1",
        platforms = "PC",
        website = "site1",
        reddit = "reddit1",
        metacritic = "meta1",
        isFavorite = false
    )

    private val detail2Favorite = detail1.copy(id = 2, name = "Favorite Game", isFavorite = true)

    @Before
    fun setup() {
        dataSource = FakeLocalDataSource()
    }

    @Test
    fun `getAllGames returns empty list initially`() = runTest {
        val result = dataSource.getAllGames().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `insertGames adds data to local storage`() = runTest {
        dataSource.insertGames(listOf(gameList1, gameList2))
        val result = dataSource.getAllGames().first()
        assertEquals(2, result.size)
        assertEquals("Game One", result[0].name)
    }

    @Test
    fun `deleteAllGames clears game list`() = runTest {
        dataSource.insertGames(listOf(gameList1))
        dataSource.deleteAllGames()
        val result = dataSource.getAllGames().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `insertGame adds a new game detail`() = runTest {
        dataSource.insertGame(detail1)
        val result = dataSource.getGameDetail(detail1.id).first()
        assertEquals(detail1.name, result?.name)
    }

    @Test
    fun `updateFavoriteGame updates favorite flag and reflects in favorite list`() = runTest {
        dataSource.insertGame(detail1)
        val updated = detail1.copy(isFavorite = true)
        dataSource.updateFavoriteGame(updated)

        val favList = dataSource.getFavoriteGames().first()
        assertEquals(1, favList.size)
        assertEquals(true, favList[0].isFavorite)
    }

    @Test
    fun `getFavoriteGames returns only favorite games`() = runTest {
        dataSource.insertGame(detail1)
        dataSource.insertGame(detail2Favorite)

        val favorites = dataSource.getFavoriteGames().first()
        assertEquals(1, favorites.size)
        assertEquals("Favorite Game", favorites[0].name)
    }

    @Test
    fun `getGameDetail returns null if not found`() = runTest {
        val result = dataSource.getGameDetail(999).first()
        assertNull(result)
    }
}