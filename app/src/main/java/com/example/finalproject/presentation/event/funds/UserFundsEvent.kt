package com.example.finalproject.presentation.event.funds

sealed class UserFundsEvent {

    data object GetUserName : UserFundsEvent()
    data object BackButtonPressed : UserFundsEvent()
    data object ResetFlow : UserFundsEvent()
    data class AddFunds(val amount : String, val cardNumber : String) : UserFundsEvent()
    data object OpenCardsBottomSheet : UserFundsEvent()
    data object OpenNewCardBottomSheet : UserFundsEvent()
}