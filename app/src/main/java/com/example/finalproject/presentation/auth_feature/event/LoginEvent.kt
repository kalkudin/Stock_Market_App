package com.example.finalproject.presentation.auth_feature.event

sealed class LoginEvent {
    data object ResetFlow : LoginEvent()
    data object BackPressed : LoginEvent()
    data object UserNotAuthenticatedPressed : LoginEvent()
    data object ForgotPasswordPressed : LoginEvent()
    data class Login(val email : String, val password : String) : LoginEvent()
    data class LoginAndRememberUser(val email : String, val password : String) : LoginEvent()
}