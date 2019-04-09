package com.example.stories.network

import com.example.stories.model.StoryResponse
import kotlinx.coroutines.Deferred
import kotlin.coroutines.CoroutineContext

interface INetworkClient {
    fun getStories(coroutineContext: CoroutineContext): Deferred<StoryResponse?>
}