package com.example.finalproject.presentation.stock_feature.ones_to_watch_extensive_list.event

import com.example.finalproject.presentation.stock_feature.home.model.Stock

sealed class ExtensiveListEvent {
    data class GetExtensiveStocksList(val performingType : Stock.PerformingType) : ExtensiveListEvent()
    data class NavigateToStockDetailsPage(val stock : Stock) : ExtensiveListEvent()
    data object NavigateToStockHomeFragment : ExtensiveListEvent()
}