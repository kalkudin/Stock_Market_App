package com.example.finalproject.data.common

import com.example.finalproject.data.remote.model.UserDto
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HandleAuthentication @Inject constructor() {
    fun <T> authenticate(
        user: UserDto,
        operation: (String, String) -> Task<AuthResult>,
        onSuccess: (AuthResult) -> T
    ): Flow<Resource<T>> = flow {
        emit(Resource.Loading(loading = true))
        try {
            val result = operation(user.email, user.password).await()
            result.user?.let {
                emit(Resource.Success(onSuccess(result)))
            } ?: emit(Resource.Error(ErrorType.UnknownError("Authentication failed")))
        } catch (e: FirebaseAuthWeakPasswordException) {
            emit(Resource.Error(ErrorType.WeakPassword))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(Resource.Error(ErrorType.InvalidCredentials))
        } catch (e: FirebaseAuthUserCollisionException) {
            emit(Resource.Error(ErrorType.UserCollision))
        } catch (e: FirebaseAuthInvalidUserException) {
            emit(Resource.Error(ErrorType.UserNotFound))
        } catch (e: FirebaseAuthEmailException) {
            emit(Resource.Error(ErrorType.EmailError))
        } catch (e: FirebaseNetworkException) {
            emit(Resource.Error(ErrorType.NetworkError))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.UnknownError(e.message ?: "Unknown error")))
        }
        emit(Resource.Loading(loading = false))
    }
}