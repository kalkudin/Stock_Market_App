package com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.event

sealed class GetUserCardsEvent {
    data object GetUserCreditCards : GetUserCardsEvent()
}