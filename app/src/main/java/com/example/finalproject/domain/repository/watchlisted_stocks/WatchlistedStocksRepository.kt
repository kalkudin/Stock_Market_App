package com.example.finalproject.domain.repository.watchlisted_stocks

import com.example.finalproject.domain.model.company_details.GetCompanyDetails
import com.example.finalproject.domain.model.user.GetUserId
import kotlinx.coroutines.flow.Flow

interface WatchlistedStocksRepository {

    suspend fun insertUser(user: GetUserId)
    suspend fun insertStock(stock: GetCompanyDetails)
    suspend fun insertWatchlistStock(user: GetUserId, stock: GetCompanyDetails)
    suspend fun deleteWatchlistStock(user: GetUserId, stock: GetCompanyDetails)
    suspend fun getFavouriteStocksForUser(user: GetUserId): Flow<List<GetCompanyDetails>>

    suspend fun isStockInWatchlist(userId: String, symbol: String): Flow<Boolean>
}