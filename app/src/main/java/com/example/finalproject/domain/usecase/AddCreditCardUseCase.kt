package com.example.finalproject.domain.usecase

import android.util.Log
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.CreditCardDomainModel
import com.example.finalproject.domain.repository.AddCreditCardRepository
import com.example.finalproject.domain.util.CreditCardValidationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AddCreditCardUseCase @Inject constructor(
    private val validationUtil: CreditCardValidationUtil,
    private val addCreditCardRepository: AddCreditCardRepository
) {
    suspend operator fun invoke(uid: String, cardNumber: List<String>, expirationDate: String, ccv: String): Flow<Resource<Boolean>> {

        Log.d("Credit", cardNumber.toString())
        Log.d("Credit", cardNumber.joinToString(separator = ""))

        if(!validationUtil.validateCreditCardNumber(cardNumber = cardNumber.joinToString(separator = ""))) {
            return flowOf(Resource.Error(ErrorType.InvalidCardNumber))
        }

        if(!validationUtil.validateCcv(ccv = ccv)) {
            return flowOf(Resource.Error(ErrorType.InvalidCCV))
        }

        if(!validationUtil.validateDate(expirationDate)) {
            return flowOf(Resource.Error(ErrorType.InvalidExpirationDate))
        }

        return addCreditCardRepository.addCreditCard(
            uid = uid,
            creditCard = CreditCardDomainModel(
                id = validationUtil.generateUniqueId(),
                cardNumber = cardNumber.joinToString(separator = ""),
                expirationDate = expirationDate,
                ccv =ccv)
        )
    }
}
