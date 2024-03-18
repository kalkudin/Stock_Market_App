package com.example.finalproject.domain.repository.auth.reset_password

import com.example.finalproject.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface ResetPasswordRepository {
    fun resetPassword(email: String): Flow<Resource<Boolean>>
}