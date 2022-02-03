package com.example.union.model

import com.google.gson.annotations.SerializedName

data class ChatModel(
    @SerializedName("Status")
    val Status: Boolean,

    @SerializedName("Message")
    val Message: String,

    @SerializedName("FriendList")
    val FriendList: List<FriendsList> = listOf()

)

data class FriendsList(
    @SerializedName("Name")
    val Name: String,
    @SerializedName("ProfileImage")
    val ProfileImage: String,
    @SerializedName("LastMessage")
    val LastMessage: String
)
