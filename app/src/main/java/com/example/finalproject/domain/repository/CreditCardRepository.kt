package com.example.finalproject.domain.repository

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.CreditCardDomainModel
import kotlinx.coroutines.flow.Flow

interface CreditCardRepository {
    suspend fun addCreditCard(uid: String, creditCard: CreditCardDomainModel): Flow<Resource<Boolean>>
    suspend fun getAllCreditCards(uid: String): Flow<Resource<List<CreditCardDomainModel>>>
    suspend fun cardExists(uid: String, cardNumber: String): Flow<Boolean>
    suspend fun removeCreditCard(uid: String, cardId: String): Flow<Resource<Boolean>>
}