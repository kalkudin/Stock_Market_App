package com.example.finalproject.domain.usecase.user_funds_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.user_funds.GetUserFunds
import com.example.finalproject.domain.repository.firestore.user_funds.UserFundsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveUserFundsUseCase @Inject constructor(private val userFundsRepository: UserFundsRepository) {
    suspend operator fun invoke(userFunds : GetUserFunds) : Flow<Resource<Boolean>> {
        return userFundsRepository.removeUserFunds(userFunds = userFunds)
    }
}