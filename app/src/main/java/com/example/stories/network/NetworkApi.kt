package com.example.stories.network

import com.example.stories.model.StoryResponse
import retrofit2.http.GET

@JvmSuppressWildcards
interface NetworkApi {
    @GET("stories?offset=0&limit=10&fields=stories(id,title,cover,user)&filter=new")
    suspend fun getStories(): StoryResponse
}