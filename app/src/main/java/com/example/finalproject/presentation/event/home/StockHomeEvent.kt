package com.example.finalproject.presentation.event.home

import com.example.finalproject.presentation.model.home.Stock

sealed class StockHomeEvent {
    data object LogOut : StockHomeEvent()
    data object GetStocksAndUserData : StockHomeEvent()
    data object NavigateToAddFundsFragment : StockHomeEvent()
    data class NavigateToStockDetailsPage(val stock : Stock) : StockHomeEvent()
    data class NavigateToExtensiveListPage(val stockType : Stock.PerformingType) : StockHomeEvent()
}