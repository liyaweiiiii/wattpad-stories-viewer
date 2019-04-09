package com.example.stories.data

import androidx.lifecycle.LiveData
import com.example.stories.model.Story
import com.example.stories.network.INetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private val network: INetworkClient, private val storyDao: StoryDao) {

    val stories: LiveData<List<Story>> by lazy {
        storyDao.loadStories()
    }

    suspend fun refreshStories() {
        val result = network.getStories(Dispatchers.IO).await()
        withContext(Dispatchers.IO) {
            try {
                result?.let {
                    storyDao.insertStories(it.stories)
                }
            } catch (e: Exception) {
                throw e
            }

        }
    }
}