package com.example.finalproject.domain.usecase.database_usecase

import com.example.finalproject.domain.model.company_details.GetCompanyDetails
import com.example.finalproject.domain.model.user.GetUserId
import com.example.finalproject.domain.repository.watchlisted_stocks.WatchlistedStocksRepository
import javax.inject.Inject

class InsertWatchlistedStocksUseCase @Inject constructor(
    private val repository: WatchlistedStocksRepository
) {
    suspend operator fun invoke(user: GetUserId, stocks: GetCompanyDetails) {
        repository.insertWatchlistStock(user, stocks)
    }
}