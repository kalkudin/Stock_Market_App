package com.example.finalproject.presentation.state.login

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val errorMessage: String? = null
)