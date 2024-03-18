package com.example.finalproject.domain.usecase.credit_card_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.credit_card.GetCreditCard
import com.example.finalproject.domain.repository.firestore.credit_card.CreditCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserCreditCardsUseCase @Inject constructor(private val creditCardRepository: CreditCardRepository) {
    suspend operator fun invoke(uid : String) : Flow<Resource<List<GetCreditCard>>> {
        return creditCardRepository.getAllCreditCards(uid = uid)
    }
}