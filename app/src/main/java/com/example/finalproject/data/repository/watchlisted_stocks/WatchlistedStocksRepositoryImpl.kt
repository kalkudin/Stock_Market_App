package com.example.finalproject.data.repository.watchlisted_stocks

import com.example.finalproject.data.local.dao.stocks.StocksDao
import com.example.finalproject.data.local.dao.user.UserDao
import com.example.finalproject.data.local.mapper.stocks.toData
import com.example.finalproject.data.local.mapper.stocks.toDomain
import com.example.finalproject.data.local.mapper.user.toData
import com.example.finalproject.data.local.mapper.user_stocks_cross_ref.toData
import com.example.finalproject.domain.model.company_details.CompanyDetails
import com.example.finalproject.domain.model.user.UserId
import com.example.finalproject.domain.model.user_stocks_cross_ref.UserStocksCrossRef
import com.example.finalproject.domain.repository.watchlisted_stocks.WatchlistedStocksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchlistedStocksRepositoryImpl @Inject constructor(
    private val stocksDao: StocksDao,
    private val userDao: UserDao
) : WatchlistedStocksRepository {
    override suspend fun insertUser(user: UserId) {
       userDao.insertUser(user.toData())
    }

    override suspend fun insertStock(stock: CompanyDetails) {
        stocksDao.insertStocks(stock.toData())
    }

    override suspend fun insertWatchlistStock(user: UserId, stock: CompanyDetails) {
        userDao.insertUser(user.toData())

        stocksDao.insertStocks(stock.toData())

        userDao.insertUserStocksCrossRef(UserStocksCrossRef(user.id, stock.symbol).toData())
    }

    override suspend fun deleteWatchlistStock(user: UserId, stock: CompanyDetails) {
        val crossRef = UserStocksCrossRef(user.id, stock.symbol)
        userDao.deleteUserStocksCrossRef(crossRef.toData())
    }

    override suspend fun getFavouriteStocksForUser(user: UserId): Flow<List<CompanyDetails>> {
        return userDao.getWatchlistedStocksForUser(user.id).map { stockList ->
            stockList.map { it.toDomain() }
        }
    }

    override suspend fun isStockInWatchlist(userId: String, symbol: String): Flow<Boolean> {
        return userDao.isStockInWatchlist(userId, symbol)
    }
}