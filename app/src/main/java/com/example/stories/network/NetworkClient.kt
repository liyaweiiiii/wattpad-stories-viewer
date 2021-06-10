package com.example.stories.network

import com.example.stories.model.StoryResponse
import javax.inject.Inject

class NetworkClient @Inject constructor(
    private val networkApi: NetworkApi
) : INetworkClient {

    override suspend fun getStories(): StoryResponse {
        return networkApi.getStories()
    }
}
