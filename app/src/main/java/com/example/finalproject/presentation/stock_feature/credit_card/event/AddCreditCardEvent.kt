package com.example.finalproject.presentation.stock_feature.credit_card.event

sealed class AddCreditCardEvent {
    data object BackButtonPressed : AddCreditCardEvent()
    data class AddCreditCard(val cardNumber: List<String>, val expirationDate: String, val ccv: String, ) : AddCreditCardEvent()
}