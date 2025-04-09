package com.saifur.gamezone.core.data.source.remote.config

import com.saifur.gamezone.core.data.source.remote.config.fake.FakeEndpoint
import com.saifur.gamezone.core.data.source.remote.response.GameListResponse
import com.saifur.gamezone.core.data.source.remote.response.ResultsItem
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

class EndpointTest {

    private lateinit var endpoint: FakeEndpoint

    @Before
    fun setup() {
        endpoint = FakeEndpoint()
    }

    @Test
    fun `getGames returns empty GameListResponse by default`() = runTest {
        val response = endpoint.getGames("dummy_key")
        assertNotNull(response)
        assertTrue(response.results.isEmpty())
    }

    @Test
    fun `getGameDetail returns dummy detail with requested id`() = runTest {
        val expectedId = 123
        val response = endpoint.getGameDetail(expectedId, "dummy_key")
        assertEquals(expectedId, response.id)
    }

    @Test
    fun `searchGame returns empty response when query not found`() = runTest {
        val response = endpoint.searchGame("non_existing_query", "dummy_key")
        assertTrue(response.results.isEmpty())
    }

    @Test
    fun `searchGame returns correct response when query exists in map`() = runTest {
        val searchResponse = GameListResponse(
            results = listOf(
                ResultsItem(id = 1, name = "Search Game", backgroundImage = "img.png", rating = 4.5)
            )
        )

        val field = FakeEndpoint::class.java.getDeclaredField("searchResponses")
        field.isAccessible = true
        val map = field.get(endpoint) as MutableMap<String, GameListResponse>
        map["search_query"] = searchResponse

        val response = endpoint.searchGame("search_query", "dummy_key")
        assertEquals(1, response.results.size)
        assertEquals("Search Game", response.results[0].name)
    }
}