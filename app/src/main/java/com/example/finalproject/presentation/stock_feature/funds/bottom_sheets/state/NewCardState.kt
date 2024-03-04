package com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.state

data class NewCardState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val success : Boolean = false
)