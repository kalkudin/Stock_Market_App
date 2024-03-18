package com.example.finalproject.data.repository.firestore.credit_card

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.credit_card.toDataModel
import com.example.finalproject.data.remote.mapper.credit_card.toDomainModel
import com.example.finalproject.data.remote.model.credit_card.CreditCardDto
import com.example.finalproject.domain.model.credit_card.GetCreditCard
import com.example.finalproject.domain.repository.firestore.credit_card.CreditCardRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CreditCardRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    CreditCardRepository {

    override suspend fun addCreditCard(uid: String, creditCard: GetCreditCard): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        try {
            val creditCardDataModel = creditCard.toDataModel()
            db.collection("users").document(uid).collection("creditCards").add(creditCardDataModel).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }

    override suspend fun getAllCreditCards(uid: String): Flow<Resource<List<GetCreditCard>>> = flow {
        emit(Resource.Loading(true))
        try {
            val snapshot = db.collection("users").document(uid).collection("creditCards").get().await()
            val cards = snapshot.documents.mapNotNull { doc ->
                doc.toObject(CreditCardDto::class.java)?.let {
                    it.id = doc.id
                    it.toDomainModel()
                }
            }
            emit(Resource.Success(cards))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }

    override suspend fun cardExists(uid: String, cardNumber: String): Flow<Boolean> = flow {
        val snapshot = db.collection("users").document(uid).collection("creditCards")
            .whereEqualTo("cardNumber", cardNumber).get().await()
        emit(!snapshot.isEmpty)
    }

    override suspend fun removeCreditCard(uid: String, cardId: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        try {
            db.collection("users").document(uid).collection("creditCards").document(cardId).delete().await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }
}