package com.example.finalproject.presentation.bottom_sheets.event

import com.example.finalproject.presentation.model.funds.CreditCard

sealed class GetUserCardsEvent {
    data object GetUserCreditCards : GetUserCardsEvent()
    data class NavigateBack(val card : CreditCard) : GetUserCardsEvent()
}