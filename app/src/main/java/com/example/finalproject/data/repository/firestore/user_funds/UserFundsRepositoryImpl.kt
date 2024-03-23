package com.example.finalproject.data.repository.firestore.user_funds

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.user_funds.toDomain
import com.example.finalproject.data.remote.mapper.user_funds.toDto
import com.example.finalproject.data.remote.model.user_funds.UserFundsDto
import com.example.finalproject.domain.model.user_funds.GetUserFunds
import com.example.finalproject.domain.repository.firestore.user_funds.UserFundsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFundsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : UserFundsRepository {

    override suspend fun addUserFunds(userFunds: GetUserFunds): Flow<Resource<Boolean>> = flow {
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
            userFundsRef.set(GetUserFunds(uid = userFunds.uid, amount = newFunds).toDto()).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }

    override suspend fun removeUserFunds(userFunds: GetUserFunds): Flow<Resource<Boolean>> = flow {
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

    override suspend fun retrieveUserFunds(uid: String): Flow<Resource<GetUserFunds>> = flow {
        emit(Resource.Loading(true))
        try {
            val userFundsRef = db.collection("userFunds").document(uid)
            val snapshot = userFundsRef.get().await()
            if (snapshot.exists()) {
                val userFunds = snapshot.toObject(UserFundsDto::class.java)?.toDomain() ?: GetUserFunds(uid, 0.0)
                emit(Resource.Success(userFunds))
            } else {
                emit(Resource.Success(GetUserFunds(uid, 0.0)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }

    //
    override suspend fun updateUserFunds(uid: String, newAmount: Double): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        try {
            val userFundsRef = db.collection("userFunds").document(uid)
            val snapshot = userFundsRef.get().await()
            if (snapshot.exists()) {
                userFundsRef.update("fundAmount", newAmount).await()
                emit(Resource.Success(true))
            } else {
                emit(Resource.Error(ErrorType.UnknownError("User funds document does not exist.")))
            }
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }
}