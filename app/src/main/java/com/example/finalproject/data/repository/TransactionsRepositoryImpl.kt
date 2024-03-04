package com.example.finalproject.data.repository

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.toDataModel
import com.example.finalproject.data.remote.mapper.toDomainModel
import com.example.finalproject.data.remote.model.TransactionDataModel
import com.example.finalproject.domain.model.GetTransaction
import com.example.finalproject.domain.repository.TransactionsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(private val db : FirebaseFirestore) : TransactionsRepository{

    override suspend fun getTransactions(uid: String): Flow<Resource<List<GetTransaction>>> = flow {
        try {
            emit(Resource.Loading(loading = true))
            val transactionsList = mutableListOf<GetTransaction>()
            val snapshot = db.collection("users").document(uid).collection("transactions").get().await()
            for (document in snapshot.documents) {
                document.toObject(TransactionDataModel::class.java)?.let { transactionDataModel ->
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