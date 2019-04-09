package com.example.stories.network

import com.example.stories.model.StoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface NetworkApi {
    @GET("stories?offset=0&limit=10&fields=stories(id,title,cover,user)&filter=new")
    fun getStories(): Call<StoryResponse>
}