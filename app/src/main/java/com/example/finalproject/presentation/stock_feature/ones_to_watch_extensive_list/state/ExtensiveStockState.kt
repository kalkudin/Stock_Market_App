package com.example.finalproject.presentation.stock_feature.ones_to_watch_extensive_list.state

import com.example.finalproject.presentation.stock_feature.home.model.Stock

data class ExtensiveStockState(
    val extensiveStocks : List<Stock>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)