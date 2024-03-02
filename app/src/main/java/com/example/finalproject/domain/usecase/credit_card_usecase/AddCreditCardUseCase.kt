package com.example.finalproject.domain.usecase.credit_card_usecase

import android.util.Log
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.CreditCardDomainModel
import com.example.finalproject.domain.repository.CreditCardRepository
import com.example.finalproject.domain.util.CreditCardValidationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AddCreditCardUseCase @Inject constructor(
    private val validationUtil: CreditCardValidationUtil,
    private val creditCardRepository: CreditCardRepository
) {
    suspend operator fun invoke(uid: String, cardNumber: List<String>, expirationDate: String, ccv: String): Flow<Resource<Boolean>> {

        if(cardNumber.joinToString(separator = "").isEmpty() && expirationDate.isEmpty() && ccv.isEmpty()) {
            return flowOf(Resource.Error(ErrorType.FieldsEmpty))
        }

        if(!validationUtil.validateCreditCardNumber(cardNumber = cardNumber.joinToString(separator = ""))) {
            return flowOf(Resource.Error(ErrorType.InvalidCardNumber))
        }

        if(!validationUtil.validateCcv(ccv = ccv)) {
            return flowOf(Resource.Error(ErrorType.InvalidCCV))
        }

        if(!validationUtil.validateDate(expirationDate)) {
            return flowOf(Resource.Error(ErrorType.InvalidExpirationDate))
        }

        val cardExists = creditCardRepository.cardExists(uid, cardNumber.joinToString(separator = "")).first()
        if (cardExists) {
            return flowOf(Resource.Error(ErrorType.CardAlreadyExists))
        }

        return creditCardRepository.addCreditCard(
            uid = uid,
            creditCard = CreditCardDomainModel(
                id = validationUtil.generateUniqueId(),
                cardNumber = cardNumber.joinToString(separator = ""),
                expirationDate = expirationDate,
                ccv =ccv)
        )
    }
}
