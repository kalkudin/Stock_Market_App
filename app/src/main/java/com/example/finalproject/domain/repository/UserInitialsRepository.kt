package com.example.finalproject.domain.repository

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.UserInitials
import kotlinx.coroutines.flow.Flow

interface UserInitialsRepository {
    suspend fun getUserInitials(uid : String) : Flow<Resource<UserInitials>>
    suspend fun saveUserInitials(uid : String, fullName : UserInitials) : Flow<Resource<Boolean>>
}