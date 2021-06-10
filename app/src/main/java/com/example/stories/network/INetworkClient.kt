package com.example.stories.network

import com.example.stories.model.StoryResponse

interface INetworkClient {
    suspend fun getStories(): StoryResponse
}