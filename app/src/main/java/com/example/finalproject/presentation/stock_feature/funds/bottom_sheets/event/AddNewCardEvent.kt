package com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.event

import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard

sealed class AddNewCardEvent {
    data class AddCreditCard(val cardNumber: List<String>, val expirationDate: String, val ccv: String, val cardType: String) : AddNewCardEvent()
}