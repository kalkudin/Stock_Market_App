package com.example.finalproject.presentation.stock_feature.home.event

sealed class StockHomeEvent {
    data object LogOut : StockHomeEvent()
}