package com.example.union.model

import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("Status")
    val Status: Boolean,

    @SerializedName("Message")
    val Message: String,

    @SerializedName("UserId")
    val UserId: Int,

    @SerializedName("Name")
    val Name: String,

    @SerializedName("Location")
    val Location: String,

    @SerializedName("Description")
    val Description: String,

    @SerializedName("ProfileImage")
    val ProfileImage: String,

    @SerializedName("PhotosCount")
    val PhotosCount: Int,

    @SerializedName("FollowersCount")
    val FollowersCount: Int,

    @SerializedName("FollowsCount")
    val FollowsCount: Int,

    @SerializedName("PhotoList")
    val PhotoList: List<PhotoList> = listOf()
)

data class PhotoList(
    @SerializedName("Image")
    val Image: String
)