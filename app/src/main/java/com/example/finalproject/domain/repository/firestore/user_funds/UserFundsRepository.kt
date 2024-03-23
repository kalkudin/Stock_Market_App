package com.example.finalproject.domain.repository.firestore.user_funds

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.user_funds.GetUserFunds
import kotlinx.coroutines.flow.Flow

interface UserFundsRepository {
    suspend fun addUserFunds(userFunds : GetUserFunds) : Flow<Resource<Boolean>>
    suspend fun removeUserFunds(userFunds: GetUserFunds) : Flow<Resource<Boolean>>
    suspend fun retrieveUserFunds(uid : String) : Flow<Resource<GetUserFunds>>

    //
    suspend fun updateUserFunds(uid: String, newAmount: Double): Flow<Resource<Boolean>>
}