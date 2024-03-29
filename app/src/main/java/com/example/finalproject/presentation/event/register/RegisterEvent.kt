package com.example.finalproject.presentation.event.register

sealed class RegisterEvent {
    data object ResetFlow : RegisterEvent()
    data object BackPressed : RegisterEvent()
    data object UserAlreadyExistsPressed : RegisterEvent()
    data class Register(
        val email : String,
        val password : String,
        val repeatPassword : String,
        val firstName : String,
        val lastName : String) : RegisterEvent()
}