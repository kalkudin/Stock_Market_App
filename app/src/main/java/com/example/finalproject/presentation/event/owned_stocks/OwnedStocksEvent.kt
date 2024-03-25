package com.example.finalproject.presentation.event.owned_stocks

import com.example.finalproject.presentation.model.owned_stocks.OwnedStock

sealed class OwnedStocksEvent {
    data object GetOwnedStocks : OwnedStocksEvent()
    data class StocksItemClick(val stock: OwnedStock) : OwnedStocksEvent()
    data class SearchOwnedStocks(val query: String) : OwnedStocksEvent()
}