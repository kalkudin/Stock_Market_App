package com.example.finalproject.data.repository

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.toData
import com.example.finalproject.data.remote.mapper.toDomain
import com.example.finalproject.data.remote.model.UserInitialsDataModel
import com.example.finalproject.domain.model.UserInitials
import com.example.finalproject.domain.repository.UserInitialsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserInitialsRepositoryImpl @Inject constructor(private val db : FirebaseFirestore): UserInitialsRepository {

    override suspend fun getUserInitials(uid: String): Flow<Resource<UserInitials>> = flow {
        emit(Resource.Loading(true))
        try {
            val snapshot = db.collection("users").document(uid).collection("userInitials").get().await()
            if (!snapshot.isEmpty) {
                val document = snapshot.documents[0]
                val firstName = document.getString("firstName") ?: ""
                val lastName = document.getString("lastName") ?: ""
                val userInitials = UserInitialsDataModel(firstName, lastName)
                emit(Resource.Success(userInitials.toDomain()))
            } else {
                emit(Resource.Error(ErrorType.UnknownError(message = "No Such Users Found")))
            }
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }

    override suspend fun saveUserInitials(uid: String, fullName: UserInitials): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading(true))
        try {
            val userInitials = fullName.toData()
            db.collection("users").document(uid).collection("userInitials").add(userInitials).await()
            emit(Resource.Success(data = true))
        }
        catch (e : Exception) {
            emit(Resource.Error(ErrorType.UnknownError(message = e.toString())))
        }
    }
}