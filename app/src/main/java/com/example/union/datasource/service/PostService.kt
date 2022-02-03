package com.example.union.datasource.service

import com.example.union.model.PostModel
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostService {

    @Headers(
        "Accept: application/json",
        "Token: 123",
        "AppVersion: 100"
    )
    @POST("PostData?PageNo=1")
    suspend fun getPostsData() :PostModel


}