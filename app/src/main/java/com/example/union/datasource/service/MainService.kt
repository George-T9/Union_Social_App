package com.example.union.datasource.service

import com.example.union.model.MainModel
import retrofit2.http.Headers
import retrofit2.http.POST

interface MainService {

    @Headers(
        "Accept: application/json",
        "Token: 123",
        "AppVersion: 100"
    )
    @POST("main")
    suspend fun getMainData(): MainModel

}
