package com.example.finalproject.domain.usecase.credit_card_usecase

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.credit_card.GetCreditCard
import com.example.finalproject.domain.repository.firestore.credit_card.CreditCardRepository
import com.example.finalproject.domain.util.CreditCardValidationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AddCreditCardUseCase @Inject constructor(
    private val validationUtil: CreditCardValidationUtil,
    private val creditCardRepository: CreditCardRepository
) {
    suspend operator fun invoke(uid: String, cardNumber: List<String>, expirationDate: String, ccv: String, cardType : String): Flow<Resource<Boolean>> {

        if(cardType.isEmpty()) {
            return flowOf(Resource.Error(ErrorType.InvalidCardType))
        }

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
            creditCard = GetCreditCard(
                id = validationUtil.generateUniqueId(),
                cardNumber = cardNumber.joinToString(separator = ""),
                expirationDate = expirationDate,
                ccv =ccv,
                type = cardType)
        )
    }
}
