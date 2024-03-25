package com.example.finalproject.presentation.bottom_sheets.event

import com.example.finalproject.presentation.model.credit_card.CreditCard

sealed class GetUserCardsEvent {
    data object GetUserCreditCards : GetUserCardsEvent()
    data class NavigateBack(val card : CreditCard) : GetUserCardsEvent()

    data class UpdateDots(val position : Int) : GetUserCardsEvent()
}