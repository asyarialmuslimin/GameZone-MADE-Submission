package com.saifur.gamezone.core.data.source.remote.datasource

import com.saifur.gamezone.core.data.source.remote.config.ApiResponse
import com.saifur.gamezone.core.data.source.remote.datasource.fake.FakeRemoteDataSource
import com.saifur.gamezone.core.data.source.remote.response.GameListResponse
import com.saifur.gamezone.core.data.source.remote.response.ResultsItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RemoteDataSourceTest {

    private lateinit var remoteDataSource: FakeRemoteDataSource

    @Before
    fun setup() {
        remoteDataSource = FakeRemoteDataSource()
    }

    @Test
    fun `getAllGames returns success with default empty list`() = runTest {
        val result = remoteDataSource.getAllGames().first()
        assertTrue(result is ApiResponse.Success)
        val data = (result as ApiResponse.Success).data
        assertTrue(data.results.isEmpty())
    }

    @Test
    fun `getGameDetail returns success with correct id`() = runTest {
        val expectedId = 101
        val result = remoteDataSource.getGameDetail(expectedId).first()
        assertTrue(result is ApiResponse.Success)
        val data = (result as ApiResponse.Success).data
        assertEquals(expectedId, data.id)
    }

    @Test
    fun `searchGame returns success when query exists`() = runTest {
        val query = "mario"
        val expectedResponse = GameListResponse(
            results = listOf(ResultsItem(id = 1, name = "Mario", backgroundImage = "mario.jpg", rating = 4.9))
        )

        val field = FakeRemoteDataSource::class.java.getDeclaredField("searchResponses")
        field.isAccessible = true
        val map = field.get(remoteDataSource) as MutableMap<String, GameListResponse>
        map[query] = expectedResponse

        val result = remoteDataSource.searchGame(query).first()
        assertTrue(result is ApiResponse.Success)
        val data = (result as ApiResponse.Success).data
        assertEquals("Mario", data.results.first().name)
    }

    @Test
    fun `searchGame returns empty when query not found`() = runTest {
        val result = remoteDataSource.searchGame("not_found_query").first()
        assertTrue(result is ApiResponse.Empty)
    }
}
