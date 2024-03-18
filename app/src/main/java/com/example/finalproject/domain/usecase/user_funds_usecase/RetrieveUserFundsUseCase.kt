package com.example.finalproject.domain.usecase.user_funds_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.user_funds.GetUserFunds
import com.example.finalproject.domain.repository.firestore.user_funds.UserFundsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveUserFundsUseCase @Inject constructor(private val userFundsRepository: UserFundsRepository) {
    suspend operator fun invoke(uid : String) : Flow<Resource<GetUserFunds>> {
        return userFundsRepository.retrieveUserFunds(uid = uid)
    }
}