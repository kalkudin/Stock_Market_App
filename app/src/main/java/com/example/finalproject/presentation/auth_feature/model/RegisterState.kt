package com.example.finalproject.presentation.auth_feature.model

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)