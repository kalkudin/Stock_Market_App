package com.example.finalproject.presentation.stock_feature.home.state

import com.example.finalproject.domain.model.UserInitials
import com.example.finalproject.presentation.profile_feature.model.UserInfo
import com.example.finalproject.presentation.stock_feature.home.model.Stock

data class StockListState(
    val bestPerformingStocks : List<Stock>? = null,
    val worstPerformingStocks : List<Stock>? = null,
    val activePerformingStocks : List<Stock>? = null,
    val userInitials : UserInfo? = null,
    val userFunds : String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)