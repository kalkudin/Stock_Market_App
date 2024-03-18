package com.example.finalproject.domain.repository.auth.register

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.user.GetUser
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun registerUser(user : GetUser): Flow<Resource<String>>
}