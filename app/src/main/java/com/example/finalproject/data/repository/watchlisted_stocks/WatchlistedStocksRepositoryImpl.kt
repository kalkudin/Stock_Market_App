package com.example.finalproject.data.repository.watchlisted_stocks

import com.example.finalproject.data.local.dao.stocks.StocksDao
import com.example.finalproject.data.local.dao.user.UserDao
import com.example.finalproject.data.local.mapper.stocks.toData
import com.example.finalproject.data.local.mapper.stocks.toDomain
import com.example.finalproject.data.local.mapper.user.toData
import com.example.finalproject.data.local.mapper.user_stocks_cross_ref.toData
import com.example.finalproject.domain.model.company_details.GetCompanyDetails
import com.example.finalproject.domain.model.user.GetUserId
import com.example.finalproject.domain.model.user_stocks_cross_ref.GetUserStocksCrossRef
import com.example.finalproject.domain.repository.watchlisted_stocks.WatchlistedStocksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchlistedStocksRepositoryImpl @Inject constructor(
    private val stocksDao: StocksDao,
    private val userDao: UserDao
) : WatchlistedStocksRepository {
    override suspend fun insertUser(user: GetUserId) {
       userDao.insertUser(user.toData())
    }

    override suspend fun insertStock(stock: GetCompanyDetails) {
        stocksDao.insertStocks(stock.toData())
    }

    override suspend fun insertWatchlistStock(user: GetUserId, stock: GetCompanyDetails) {
        userDao.insertUser(user.toData())

        stocksDao.insertStocks(stock.toData())

        userDao.insertUserStocksCrossRef(GetUserStocksCrossRef(user.id, stock.symbol).toData())
    }

    override suspend fun deleteWatchlistStock(user: GetUserId, stock: GetCompanyDetails) {
        val crossRef = GetUserStocksCrossRef(user.id, stock.symbol)
        userDao.deleteUserStocksCrossRef(crossRef.toData())
    }

    override suspend fun getFavouriteStocksForUser(user: GetUserId): Flow<List<GetCompanyDetails>> {
        return userDao.getWatchlistedStocksForUser(user.id).map { stockList ->
            stockList.map { it.toDomain() }
        }
    }

    override suspend fun isStockInWatchlist(userId: String, symbol: String): Flow<Boolean> {
        return userDao.isStockInWatchlist(userId, symbol)
    }
}