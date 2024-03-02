package com.example.finalproject.presentation.stock_feature.funds.event

sealed class UserFundsEvent {
    data object BackButtonPressed : UserFundsEvent()
}