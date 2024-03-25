package com.example.finalproject.domain.repository.firestore.user_initials

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.user_initials.GetUserInitials
import kotlinx.coroutines.flow.Flow

interface UserInitialsRepository {
    suspend fun getUserInitials(uid : String) : Flow<Resource<GetUserInitials>>
    suspend fun saveUserInitials(uid : String, fullName : GetUserInitials) : Flow<Resource<Boolean>>
}