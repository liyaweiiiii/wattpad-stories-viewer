package com.example.stories.data

import com.example.stories.model.Result.Status
import com.example.stories.network.NetworkClient
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DataRepositoryTest {
    private lateinit var repo: DataRepository

    @MockK
    private lateinit var mockNetworkClient: NetworkClient

    @MockK
    private lateinit var mockStoryDao: StoryDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { mockNetworkClient.getStories() } throws Exception()
        every { mockStoryDao.loadStories() } returns null
        every { mockStoryDao.deleteAll() } returns Unit
        every { mockStoryDao.insertStories(any()) } returns Unit

        repo = DataRepository(
            mockNetworkClient,
            mockStoryDao,
            testDispatcher
        )
    }

    @Test
    fun testFetchStories() = runBlocking {
        val result = repo.fetchStories().toList()
        println(result)
        assertTrue(
            result[0].status == Status.SUCCESS
        )
        assertTrue(
            result[1].status == Status.LOADING
        )
    }
}