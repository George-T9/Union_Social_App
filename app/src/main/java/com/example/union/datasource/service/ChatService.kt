package com.example.union.datasource.service

import com.example.union.model.ChatModel
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatService {

    @Headers(
        "Accept: application/json",
        "Token: 123",
        "AppVersion: 100"
    )
    @POST("GetConversation")
    suspend fun getAllChat() : ChatModel
}