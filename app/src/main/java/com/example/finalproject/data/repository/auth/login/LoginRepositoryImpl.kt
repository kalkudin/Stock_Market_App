package com.example.finalproject.data.repository.auth.login

import com.example.finalproject.data.common.HandleAuthentication
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.user.toDto
import com.example.finalproject.domain.model.user.GetUser
import com.example.finalproject.domain.repository.auth.login.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val handleAuthentication: HandleAuthentication,
    private val firebaseAuth: FirebaseAuth
) : LoginRepository {
    override fun loginUser(user: GetUser): Flow<Resource<String>> {
        return handleAuthentication.authenticate(
            user.toDto(),
            operation = { email, password -> firebaseAuth.signInWithEmailAndPassword(email, password) },
            onSuccess = { authResult -> authResult.user?.uid ?: throw IllegalStateException("User ID not found") }
        )
    }
}