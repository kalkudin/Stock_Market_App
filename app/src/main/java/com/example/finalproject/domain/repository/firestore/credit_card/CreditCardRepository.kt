package com.example.finalproject.domain.repository.firestore.credit_card

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.credit_card.GetCreditCard
import kotlinx.coroutines.flow.Flow

interface CreditCardRepository {
    suspend fun addCreditCard(uid: String, creditCard: GetCreditCard): Flow<Resource<Boolean>>
    suspend fun getAllCreditCards(uid: String): Flow<Resource<List<GetCreditCard>>>
    suspend fun cardExists(uid: String, cardNumber: String): Flow<Boolean>
    suspend fun removeCreditCard(uid: String, cardId: String): Flow<Resource<Boolean>>
}