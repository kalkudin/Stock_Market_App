package com.example.finalproject.presentation.state.funds

data class FundsState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val success : Boolean = false
)