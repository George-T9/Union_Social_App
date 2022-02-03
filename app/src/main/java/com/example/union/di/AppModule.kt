package com.example.union.di

import com.example.union.CoroutineDispatcherProvider
import com.example.union.datasource.service.ChatService
import com.example.union.datasource.service.MainService
import com.example.union.datasource.service.PostService
import com.example.union.datasource.service.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun mainRetrofit(): MainService = Retrofit.Builder()
        .client(getOkHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MainService::class.java)


    @Provides
    fun postRetrofit(): PostService =
        Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostService::class.java)

    @Provides
    fun profileRetrofit(): ProfileService =
        Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileService::class.java)

    @Provides
    fun chatRetrofit(): ChatService =
        Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatService::class.java)

    private fun getOkHttpClient() =
        OkHttpClient.Builder().connectTimeout(NETWORK_REQUEST_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(
                NETWORK_REQUEST_TIMEOUT_SECOND, TimeUnit.SECONDS
            ).build()

    @Provides
    fun provideCoroutineDispatcher() = CoroutineDispatcherProvider()

    companion object {
        const val NETWORK_REQUEST_TIMEOUT_SECOND = 15L
        const val BASE_URL = "https://app.smartkeeda.com/demoapi/demo/"
    }
}