package com.example.finalproject.presentation.state.watchlisted_stocks

import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel

data class WatchlistedStocksState(
    val watchlistedStocks: List<CompanyDetailsModel>? = null,
    val originalWatchlistedStocks: List<CompanyDetailsModel>? = null,
    val errorMessage: String? = null
)