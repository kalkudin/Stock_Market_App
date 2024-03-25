package com.example.finalproject.domain.usecase.database_usecase

import com.example.finalproject.domain.repository.watchlisted_stocks.WatchlistedStocksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsStockInWatchListUseCase @Inject constructor(
    private val repository: WatchlistedStocksRepository
){
    suspend operator fun invoke(userId: String, symbol: String): Flow<Boolean> {
        return repository.isStockInWatchlist(userId, symbol)
    }
}