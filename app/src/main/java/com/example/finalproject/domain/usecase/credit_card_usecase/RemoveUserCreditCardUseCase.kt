package com.example.finalproject.domain.usecase.credit_card_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.repository.firestore.credit_card.CreditCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveUserCreditCardUseCase @Inject constructor(private val creditCardRepository: CreditCardRepository) {
    suspend operator fun invoke(uid : String, cardId : String) : Flow<Resource<Boolean>> {
        return creditCardRepository.removeCreditCard(uid = uid, cardId = cardId)
    }
}