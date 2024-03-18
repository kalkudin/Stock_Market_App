package com.example.finalproject.data.repository.firestore.user_initials

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.user_initials.toData
import com.example.finalproject.data.remote.mapper.user_initials.toDomain
import com.example.finalproject.data.remote.model.user_initials.UserInitialsDto
import com.example.finalproject.domain.model.user_initials.GetUserInitials
import com.example.finalproject.domain.repository.firestore.user_initials.UserInitialsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserInitialsRepositoryImpl @Inject constructor(private val db : FirebaseFirestore):
    UserInitialsRepository {

    override suspend fun getUserInitials(uid: String): Flow<Resource<GetUserInitials>> = flow {
        emit(Resource.Loading(true))
        try {
            val snapshot = db.collection("users").document(uid).collection("userInitials").get().await()
            if (!snapshot.isEmpty) {
                val document = snapshot.documents[0]
                val firstName = document.getString("firstName") ?: ""
                val lastName = document.getString("lastName") ?: ""
                val userInitials = UserInitialsDto(firstName, lastName)
                emit(Resource.Success(userInitials.toDomain()))
            } else {
                emit(Resource.Error(ErrorType.UnknownError(message = "No Such Users Found")))
            }
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.NetworkError))
        }
    }

    override suspend fun saveUserInitials(uid: String, fullName: GetUserInitials): Flow<Resource<Boolean>> = flow{
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