package com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.event

import com.example.finalproject.presentation.stock_feature.credit_card.event.AddCreditCardEvent

sealed class AddNewCardEvent {
    data class AddCreditCard(val cardNumber: List<String>, val expirationDate: String, val ccv: String) : AddNewCardEvent()
}