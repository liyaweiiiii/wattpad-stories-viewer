package com.example.stories.data

import com.example.stories.model.Result
import com.example.stories.model.Story
import kotlinx.coroutines.flow.Flow

interface IDataRepository {
    fun fetchStories(): Flow<Result<List<Story>?>>
}
