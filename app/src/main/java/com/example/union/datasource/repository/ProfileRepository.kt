package com.example.union.datasource.repository

import com.example.union.datasource.service.ProfileService
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileService: ProfileService){
    suspend fun getProfileData() = profileService.getProfileData()
}