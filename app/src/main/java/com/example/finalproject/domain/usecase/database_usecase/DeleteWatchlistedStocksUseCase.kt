package com.example.finalproject.domain.usecase.database_usecase

import com.example.finalproject.domain.model.company_details.CompanyDetails
import com.example.finalproject.domain.model.user.UserId
import com.example.finalproject.domain.repository.watchlisted_stocks.WatchlistedStocksRepository
import javax.inject.Inject

class DeleteWatchlistedStocksUseCase @Inject constructor(
    private val repository: WatchlistedStocksRepository
) {
    suspend operator fun invoke(user: UserId, stock:CompanyDetails) {
        repository.deleteWatchlistStock(user, stock)
    }
}