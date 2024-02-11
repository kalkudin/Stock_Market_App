package com.example.finalproject.domain.repository

import com.example.finalproject.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface ResetPasswordRepository {
    fun resetPassword(email: String): Flow<Resource<Boolean>>
}