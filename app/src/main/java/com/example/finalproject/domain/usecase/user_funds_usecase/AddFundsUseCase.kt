package com.example.finalproject.domain.usecase.user_funds_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.user_funds.GetUserFunds
import com.example.finalproject.domain.repository.firestore.user_funds.UserFundsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFundsUseCase @Inject constructor(
    private val userFundsRepository: UserFundsRepository
) {
    suspend operator fun invoke(uid: String, amount: String): Flow<Resource<Boolean>> {
        return userFundsRepository.addUserFunds(userFunds = GetUserFunds(uid = uid, amount = amount.toDouble()))
    }
}