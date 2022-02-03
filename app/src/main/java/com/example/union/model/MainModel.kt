package com.example.union.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MainModel(

    @SerializedName("Status")
    val Status: Boolean,

    @SerializedName("Message")
    val Message: String,

    @SerializedName("UserId")
    val UserId: Int,

    @SerializedName("Name")
    val Name: String,

    @SerializedName("ProfileImage")
    val ProfileImage: String,

    @SerializedName("FriendList")
    val FriendList: List<FriendList> = emptyList(),

    @SerializedName("PageNo")
    val PageNo: Int
)

data class FriendList(

    @SerializedName("Name")
    val name: String,

    @SerializedName("ProfileImage")
    val ProfileImage: String
    )