package com.example.finalproject.domain.usecase.database_usecase

import com.example.finalproject.domain.model.company_details.GetCompanyDetails
import com.example.finalproject.domain.repository.watchlisted_stocks.WatchlistedStocksRepository
import javax.inject.Inject

class InsertStocksUseCase @Inject constructor(
    private val repository: WatchlistedStocksRepository
) {
    suspend operator fun invoke(stock:GetCompanyDetails) {
        repository.insertStock(stock)
    }
}