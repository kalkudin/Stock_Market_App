package com.example.finalproject.domain.repository.stocks_to_watch

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.stocks_to_watch.StockListItem
import kotlinx.coroutines.flow.Flow

interface StocksToWatchRepository {
    suspend fun getBestPerformingStocks() : Flow<Resource<List<StockListItem>>>
    suspend fun getWorstPerformingStocks() : Flow<Resource<List<StockListItem>>>
    suspend fun getActivePerformingStocks() : Flow<Resource<List<StockListItem>>>
}