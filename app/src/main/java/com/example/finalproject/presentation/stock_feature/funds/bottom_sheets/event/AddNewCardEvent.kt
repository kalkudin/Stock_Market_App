package com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.event

sealed class AddNewCardEvent {
    data class AddCreditCard(val cardNumber: List<String>, val expirationDate: String, val ccv: String) : AddNewCardEvent()
}