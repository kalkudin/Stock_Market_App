package com.example.finalproject.data.repository

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.toDomain
import com.example.finalproject.data.remote.mapper.toDto
import com.example.finalproject.data.remote.model.UserFundsDto
import com.example.finalproject.domain.model.UserFunds
import com.example.finalproject.domain.repository.UserFundsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFundsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : UserFundsRepository {

    override suspend fun addUserFunds(userFunds: UserFunds): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        try {
            val userFundsRef = db.collection("userFunds").document(userFunds.uid)
            val snapshot = userFundsRef.get().await()
            val newFunds = if (snapshot.exists()) {
                val currentFunds = snapshot.getDouble("fundAmount") ?: 0.0
                currentFunds + userFunds.amount
            } else {
                userFunds.amount
            }
            userFundsRef.set(UserFunds(uid = userFunds.uid, amount = newFunds).toDto()).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }

    override suspend fun removeUserFunds(userFunds: UserFunds): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        try {
            val userFundsRef = db.collection("userFunds").document(userFunds.uid)
            val snapshot = userFundsRef.get().await()
            if (snapshot.exists()) {
                val currentFunds = snapshot.getDouble("fundAmount") ?: 0.0
                val newAmount = (currentFunds - userFunds.amount).coerceAtLeast(0.0)
                userFundsRef.update("fundAmount", newAmount).await()
                emit(Resource.Success(true))
            } else {
                emit(Resource.Error(ErrorType.UnknownError("User funds document does not exist.")))
            }
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }

    override suspend fun retrieveUserFunds(uid: String): Flow<Resource<UserFunds>> = flow {
        emit(Resource.Loading(true))
        try {
            val userFundsRef = db.collection("userFunds").document(uid)
            val snapshot = userFundsRef.get().await()
            if (snapshot.exists()) {
                val userFunds = snapshot.toObject(UserFundsDto::class.java)?.toDomain() ?: UserFunds(uid, 0.0)
                emit(Resource.Success(userFunds))
            } else {
                emit(Resource.Success(UserFunds(uid, 0.0)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }
}