package com.example.stories.network

import com.example.stories.model.Story
import com.example.stories.model.StoryResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

object NetworkClient : INetworkClient {
    private val NetworkApi = Retrofit.Builder()
        .baseUrl("https://www.wattpad.com/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NetworkApi::class.java)

    override fun getStories(coroutineContext: CoroutineContext): Deferred<StoryResponse?> {
        return CoroutineScope(coroutineContext).async {
            NetworkApi.getStories().execute().body()
        }
    }
}