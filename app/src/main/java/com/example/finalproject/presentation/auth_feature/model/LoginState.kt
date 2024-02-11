package com.example.finalproject.presentation.auth_feature.model

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val errorMessage: String? = null
)