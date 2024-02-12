package com.example.finalproject.data.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HandlePasswordReset @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    fun resetPassword(email: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(loading = true))
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(Resource.Success(true))
        } catch (e: FirebaseAuthInvalidUserException) {
            emit(Resource.Error(ErrorType.UserNotFound))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(Resource.Error(ErrorType.InvalidCredentials))
        } catch (e: FirebaseAuthEmailException) {
            emit(Resource.Error(ErrorType.EmailError))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.UnknownError(e.localizedMessage ?: "An unexpected error occurred")))
        }
        emit(Resource.Loading(loading = false))
    }
}