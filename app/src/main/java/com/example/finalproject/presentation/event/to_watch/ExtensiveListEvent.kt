package com.example.finalproject.presentation.event.to_watch

import com.example.finalproject.presentation.model.home.Stock

sealed class ExtensiveListEvent {
    data class GetExtensiveStocksList(val performingType : Stock.PerformingType) : ExtensiveListEvent()
    data class NavigateToStockDetailsPage(val stock : Stock) : ExtensiveListEvent()
    data object NavigateToStockHomeFragment : ExtensiveListEvent()
}