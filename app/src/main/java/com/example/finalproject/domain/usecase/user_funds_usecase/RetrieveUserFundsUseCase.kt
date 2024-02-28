package com.example.finalproject.domain.usecase.user_funds_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.UserFunds
import com.example.finalproject.domain.repository.UserFundsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveUserFundsUseCase @Inject constructor(private val userFundsRepository: UserFundsRepository) {
    suspend operator fun invoke(uid : String) : Flow<Resource<UserFunds>> {
        return userFundsRepository.retrieveUserFunds(uid = uid)
    }
}