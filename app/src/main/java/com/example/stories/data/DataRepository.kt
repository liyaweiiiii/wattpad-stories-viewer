package com.example.stories.data

import com.example.stories.IoDispatcher
import com.example.stories.model.Result
import com.example.stories.model.Story
import com.example.stories.network.INetworkClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val network: INetworkClient,
    private val storyDao: StoryDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): IDataRepository {
    override fun fetchStories(): Flow<Result<List<Story>?>> {
        return flow<Result<List<Story>?>> {
            emit(Result.success(fetchStoriesCached()))
            try {
                emit(Result.loading())
                val result = network.getStories().stories

                result.let {
                    storyDao.deleteAll()
                    storyDao.insertStories(it)
                    emit(Result.event("Stories have been saved for offline use"))
                }
                emit(Result.success(result))
            } catch (e: Exception) {
                emit(Result.error(Error(e), "Error fetching stories, displaying cached content"))
            }
        }.flowOn(ioDispatcher)
    }

    private fun fetchStoriesCached(): List<Story>? =
        storyDao.loadStories()
}