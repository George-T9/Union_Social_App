package com.example.union.datasource.service

import com.example.union.model.PostModel
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface PostService {

    @Headers(
        "Accept: application/json",
        "Token: 123",
        "AppVersion: 100"
    )
    @POST("PostData")
    suspend fun getPostsData(
        @Query("PageNo") pageNumber: Int
    ): PostModel


}