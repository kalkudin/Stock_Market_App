package com.example.finalproject.presentation.stock_feature.event

sealed class StockHomeEvent {
    data object LogOut : StockHomeEvent()
}