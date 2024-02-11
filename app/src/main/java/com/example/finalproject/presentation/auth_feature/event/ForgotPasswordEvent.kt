package com.example.finalproject.presentation.auth_feature.event

sealed class ForgotPasswordEvent {
    data object BackPressed : ForgotPasswordEvent()
    data object BackToLoginPressed : ForgotPasswordEvent()
    data class ResetPassword(val email : String) : ForgotPasswordEvent()
}