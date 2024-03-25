package com.example.finalproject.presentation.bottom_sheets.event

import com.example.finalproject.presentation.model.credit_card.CreditCard

sealed class AddNewCardEvent {
    data class AddCreditCard(val cardNumber: List<String>, val expirationDate: String, val ccv: String, val cardType: String) : AddNewCardEvent()
    data object ResetErrorMessage : AddNewCardEvent()
    data class NavigateToFundsFragment(val cardNumber: List<String>, val expirationDate: String, val ccv: String, val cardType: CreditCard.CardType): AddNewCardEvent()
}