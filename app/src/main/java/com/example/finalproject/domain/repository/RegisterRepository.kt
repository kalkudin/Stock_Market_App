package com.example.finalproject.domain.repository

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.User
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun registerUser(user : User): Flow<Resource<Boolean>>
}