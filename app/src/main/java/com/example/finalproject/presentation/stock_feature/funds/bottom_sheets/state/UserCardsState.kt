package com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.state

import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard

data class UserCardsState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val cardList : List<CreditCard>? = null
)