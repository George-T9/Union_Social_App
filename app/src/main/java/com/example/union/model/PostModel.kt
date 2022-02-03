package com.example.union.model

import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("Status")
    val Status: Boolean,

    @SerializedName("Message")
    val Message: String,

    @SerializedName("PostList")
    val PostList: List<PostList>,

    @SerializedName("PageNo")
    val PageNo: Int
)

data class PostList(
    @SerializedName("Name")
    val Name: String,

    @SerializedName("ProfileImage")
    val ProfileImage: String,

    @SerializedName("PostImage")
    val PostImage: String,

    @SerializedName("Time")
    val Time: String
)