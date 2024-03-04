package com.example.finalproject.presentation.stock_feature.funds.event

sealed class UserFundsEvent {
    data object BackButtonPressed : UserFundsEvent()
    data object ResetFlow : UserFundsEvent()
    data class AddFunds(val amount : String, val cardNumber : String) : UserFundsEvent()
    data object OpenCardsBottomSheet : UserFundsEvent()
    data object OpenNewCardBottomSheet : UserFundsEvent()
}