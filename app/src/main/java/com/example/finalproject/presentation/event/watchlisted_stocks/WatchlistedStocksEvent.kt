package com.example.finalproject.presentation.event.watchlisted_stocks

import com.example.finalproject.presentation.model.company_details.CompanyDetails
import com.example.finalproject.presentation.model.company_details.UserId

sealed class WatchlistedStocksEvent {
    data class GetWatchlistedStocks(val user: UserId) : WatchlistedStocksEvent()
    data class StocksItemClick(val stock: CompanyDetails) : WatchlistedStocksEvent()
    data class SearchWatchlistedStocks(val query: String) : WatchlistedStocksEvent()
    data class DeleteWatchlistedStocks(val stock: CompanyDetails, val user: UserId) : WatchlistedStocksEvent()
}