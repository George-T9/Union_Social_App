package com.example.union.datasource.repository

import com.example.union.datasource.service.ChatService
import javax.inject.Inject

class ChatRepository @Inject constructor(private val chatService: ChatService){

    suspend fun getAllChat() = chatService.getAllChat()
}