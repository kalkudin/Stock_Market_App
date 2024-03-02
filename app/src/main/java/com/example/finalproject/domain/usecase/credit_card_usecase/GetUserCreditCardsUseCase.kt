package com.example.finalproject.domain.usecase.credit_card_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.CreditCardDomainModel
import com.example.finalproject.domain.repository.CreditCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserCreditCardsUseCase @Inject constructor(private val creditCardRepository: CreditCardRepository) {
    suspend operator fun invoke(uid : String) : Flow<Resource<List<CreditCardDomainModel>>> {
        return creditCardRepository.getAllCreditCards(uid = uid)
    }
}