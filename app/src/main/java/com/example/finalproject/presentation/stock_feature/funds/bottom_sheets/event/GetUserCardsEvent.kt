package com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.event

import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard

sealed class GetUserCardsEvent {
    data object GetUserCreditCards : GetUserCardsEvent()
    data class NavigateBack(val card : CreditCard) : GetUserCardsEvent()
}