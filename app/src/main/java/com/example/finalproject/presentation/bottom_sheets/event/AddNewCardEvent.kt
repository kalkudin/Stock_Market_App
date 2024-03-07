package com.example.finalproject.presentation.bottom_sheets.event

sealed class AddNewCardEvent {
    data class AddCreditCard(val cardNumber: List<String>, val expirationDate: String, val ccv: String, val cardType: String) : AddNewCardEvent()
}