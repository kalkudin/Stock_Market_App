package com.example.finalproject.data.repository

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.toDataModel
import com.example.finalproject.data.remote.mapper.toDomainModel
import com.example.finalproject.data.remote.model.CreditCardDataModel
import com.example.finalproject.domain.model.CreditCardDomainModel
import com.example.finalproject.domain.repository.AddCreditCardRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddCreditCardRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : AddCreditCardRepository {

    override suspend fun addCreditCard(uid: String, creditCard: CreditCardDomainModel): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        try {
            val creditCardDataModel = creditCard.toDataModel()
            db.collection("users").document(uid).collection("creditCards").add(creditCardDataModel).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }

    override suspend fun getAllCreditCards(uid: String): Flow<Resource<List<CreditCardDomainModel>>> = flow {
        emit(Resource.Loading(true))
        try {
            val snapshot = db.collection("users").document(uid).collection("creditCards").get().await()
            val cards = snapshot.documents.mapNotNull { doc ->
                doc.toObject(CreditCardDataModel::class.java)?.let {
                    it.id = doc.id
                    it.toDomainModel()
                }
            }
            emit(Resource.Success(cards))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }

}