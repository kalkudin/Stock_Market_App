package com.example.finalproject.domain.repository.watchlisted_stocks

import com.example.finalproject.domain.model.company_details.CompanyDetails
import com.example.finalproject.domain.model.user.UserId
import kotlinx.coroutines.flow.Flow

interface WatchlistedStocksRepository {

    suspend fun insertUser(user: UserId)
    suspend fun insertStock(stock: CompanyDetails)
    suspend fun insertWatchlistStock(user: UserId, stock: CompanyDetails)
    suspend fun deleteWatchlistStock(user: UserId, stock: CompanyDetails)
    suspend fun getFavouriteStocksForUser(user: UserId): Flow<List<CompanyDetails>>

    suspend fun isStockInWatchlist(userId: String, symbol: String): Flow<Boolean>
}