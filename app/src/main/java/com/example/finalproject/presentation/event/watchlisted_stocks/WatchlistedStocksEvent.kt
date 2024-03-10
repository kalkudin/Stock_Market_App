package com.example.finalproject.presentation.event.watchlisted_stocks

import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel
import com.example.finalproject.presentation.model.company_details.UserIdModel

sealed class WatchlistedStocksEvent {
    data class GetWatchlistedStocks(val user: UserIdModel) : WatchlistedStocksEvent()
    data class StocksItemClick(val stock: CompanyDetailsModel) : WatchlistedStocksEvent()
    data class SearchWatchlistedStocks(val query: String) : WatchlistedStocksEvent()
    data class DeleteWatchlistedStocks(val stock: CompanyDetailsModel, val user: UserIdModel) : WatchlistedStocksEvent()
}