package com.example.finalproject.presentation.stock_feature.funds.state

data class FundsState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val success : Boolean = false
)