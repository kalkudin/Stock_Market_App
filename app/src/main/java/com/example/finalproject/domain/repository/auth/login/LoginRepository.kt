package com.example.finalproject.domain.repository.auth.login

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.user.GetUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun loginUser(user : GetUser): Flow<Resource<String>>
}