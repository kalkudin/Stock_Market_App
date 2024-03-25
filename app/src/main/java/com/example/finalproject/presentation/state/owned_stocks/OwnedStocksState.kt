package com.example.finalproject.presentation.state.owned_stocks

import com.example.finalproject.presentation.model.owned_stocks.OwnedStock

data class OwnedStocksState (
    val ownedStocks: List<OwnedStock>? = null,
    val originalOwnedStocks: List<OwnedStock>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)