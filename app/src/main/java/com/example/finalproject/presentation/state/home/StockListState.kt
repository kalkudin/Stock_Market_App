package com.example.finalproject.presentation.state.home

import com.example.finalproject.presentation.model.profile.UserInfo
import com.example.finalproject.presentation.model.home.Stock

data class StockListState(
    val bestPerformingStocks : List<Stock>? = null,
    val worstPerformingStocks : List<Stock>? = null,
    val activePerformingStocks : List<Stock>? = null,
    val userFirstName : String? = null,
    val userFunds : String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)