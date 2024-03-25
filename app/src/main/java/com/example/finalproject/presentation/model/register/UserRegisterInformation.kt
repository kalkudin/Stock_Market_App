package com.example.finalproject.presentation.model.register

data class UserRegisterInformation(
    val email : String,
    val password : String,
    val repeatPassword : String,
    val firstName : String,
    val lastName : String,
)