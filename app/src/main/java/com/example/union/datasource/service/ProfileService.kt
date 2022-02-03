package com.example.union.datasource.service

import com.example.union.model.ProfileModel
import retrofit2.http.Headers
import retrofit2.http.POST

interface ProfileService {
    @Headers(
        "Accept: application/json",
        "Token: 123",
        "AppVersion: 100"
    )
    @POST("Getprofile?UserId=1")
    suspend fun getProfileData() : ProfileModel
}