package com.example.finalproject.presentation.state.watchlisted_stocks

import com.example.finalproject.presentation.model.company_details.CompanyDetails

data class WatchlistedStocksState(
    val watchlistedStocks: List<CompanyDetails>? = null,
    val originalWatchlistedStocks: List<CompanyDetails>? = null,
    val errorMessage: String? = null
)