package com.example.finalproject.presentation.event.forgot_password

sealed class ForgotPasswordEvent {
    data object BackPressed : ForgotPasswordEvent()
    data object BackToLoginPressed : ForgotPasswordEvent()
    data class ResetPassword(val email : String) : ForgotPasswordEvent()
}