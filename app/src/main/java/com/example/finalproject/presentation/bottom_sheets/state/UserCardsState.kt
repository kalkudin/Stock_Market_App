package com.example.finalproject.presentation.bottom_sheets.state

import com.example.finalproject.presentation.model.funds.CreditCard

data class UserCardsState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val cardList : List<CreditCard>? = null,
)