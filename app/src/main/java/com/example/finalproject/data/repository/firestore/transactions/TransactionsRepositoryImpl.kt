package com.example.finalproject.data.repository.firestore.transactions

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.transactions.toDataModel
import com.example.finalproject.data.remote.mapper.transactions.toDomainModel
import com.example.finalproject.data.remote.model.transactions.TransactionDto
import com.example.finalproject.domain.model.transactions.GetTransaction
import com.example.finalproject.domain.repository.firestore.transactions.TransactionsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(private val db : FirebaseFirestore) :
    TransactionsRepository {

    override suspend fun getTransactions(uid: String): Flow<Resource<List<GetTransaction>>> = flow {
        try {
            emit(Resource.Loading(loading = true))
            val transactionsList = mutableListOf<GetTransaction>()
            val snapshot = db.collection("users").document(uid).collection("transactions").get().await()
            for (document in snapshot.documents) {
                document.toObject(TransactionDto::class.java)?.let { transactionDataModel ->
                    transactionsList.add(transactionDataModel.toDomainModel())
                }
            }
            emit(Resource.Success(transactionsList))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.UnknownError(message = e.message.toString())))
        }
    }

    override suspend fun saveTransaction(uid: String, transaction: GetTransaction): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading(loading = true))
            db.collection("users").document(uid).collection("transactions").add(transaction.toDataModel()).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }
}