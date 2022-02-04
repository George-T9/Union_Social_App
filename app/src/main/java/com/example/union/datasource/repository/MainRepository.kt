package com.example.union.datasource.repository

import com.example.union.datasource.service.MainService
import com.example.union.datasource.service.PostService
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainService: MainService, private val postService: PostService) {

    suspend fun getMainData() = mainService.getMainData()

    suspend fun getPostData(page:Int) = postService.getPostsData(page)
}