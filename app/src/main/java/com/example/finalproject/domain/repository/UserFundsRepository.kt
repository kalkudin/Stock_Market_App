package com.example.finalproject.domain.repository

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.UserFunds
import kotlinx.coroutines.flow.Flow

interface UserFundsRepository {
    suspend fun addUserFunds(userFunds : UserFunds) : Flow<Resource<Boolean>>
    suspend fun removeUserFunds(userFunds: UserFunds) : Flow<Resource<Boolean>>
    suspend fun retrieveUserFunds(uid : String) : Flow<Resource<UserFunds>>
}