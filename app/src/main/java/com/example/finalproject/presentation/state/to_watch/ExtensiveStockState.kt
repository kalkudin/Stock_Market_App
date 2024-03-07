package com.example.finalproject.presentation.state.to_watch

import com.example.finalproject.presentation.model.home.Stock

data class ExtensiveStockState(
    val extensiveStocks : List<Stock>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)