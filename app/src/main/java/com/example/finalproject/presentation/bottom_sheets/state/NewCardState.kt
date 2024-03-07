package com.example.finalproject.presentation.bottom_sheets.state

data class NewCardState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val success : Boolean = false
)