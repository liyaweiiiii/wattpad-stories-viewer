package com.example.stories.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideNetworkApi(
        retrofit: Retrofit
    ): NetworkApi {
        return retrofit
            .create(NetworkApi::class.java)
    }

    @Provides
    fun provideINetworkClient(
        networkClient: NetworkClient
    ): INetworkClient {
        return networkClient
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.wattpad.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
