package com.example.finalproject.domain.usecase.user_funds_usecase

import android.util.Log
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.UserFunds
import com.example.finalproject.domain.repository.CreditCardRepository
import com.example.finalproject.domain.repository.UserFundsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AddUserFundsUseCase @Inject constructor(
    private val userFundsRepository: UserFundsRepository,
    private val creditCardRepository: CreditCardRepository
) {
    suspend operator fun invoke(uid: String, amount: String, cardNumber : String): Flow<Resource<Boolean>> {

        if(amount.isEmpty()) {
            return flowOf(Resource.Error(ErrorType.InvalidFunds))
        }

        val cardExists = creditCardRepository.cardExists(uid, cardNumber).first()

        if (!cardExists) {
            return flowOf(Resource.Error(ErrorType.NoSuchCreditCard))
        }

        return userFundsRepository.addUserFunds(userFunds = UserFunds(uid = uid, amount = amount.toDouble()))
    }
}