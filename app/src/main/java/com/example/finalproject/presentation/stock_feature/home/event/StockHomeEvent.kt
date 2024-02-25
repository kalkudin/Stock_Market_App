package com.example.finalproject.presentation.stock_feature.home.event

import com.example.finalproject.presentation.stock_feature.home.model.Stock

sealed class StockHomeEvent {
    data object LogOut : StockHomeEvent()
    data object GetStocksToWatch : StockHomeEvent()
    data class NavigateToStockDetailsPage(val stock : Stock) : StockHomeEvent()
    data class NavigateToExtensiveListPage(val stockType : Stock.PerformingType) : StockHomeEvent()
}