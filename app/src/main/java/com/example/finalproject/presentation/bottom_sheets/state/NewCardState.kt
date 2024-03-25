package com.example.finalproject.presentation.bottom_sheets.state

import com.example.finalproject.presentation.model.bottom_sheets.NewCardType

data class NewCardState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val success : Boolean = false,
    val cardTypeList : List<NewCardType>? = null
)