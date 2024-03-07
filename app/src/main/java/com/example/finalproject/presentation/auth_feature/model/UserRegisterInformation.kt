package com.example.finalproject.presentation.auth_feature.model

data class UserRegisterInformation(
    val email : String,
    val password : String,
    val repeatPassword : String,
    val firstName : String,
    val lastName : String,
)