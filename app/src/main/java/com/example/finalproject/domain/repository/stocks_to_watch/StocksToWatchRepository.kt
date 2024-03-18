package com.example.finalproject.domain.repository.stocks_to_watch

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.stocks_to_watch.GetStockListItem
import kotlinx.coroutines.flow.Flow

interface StocksToWatchRepository {
    suspend fun getBestPerformingStocks() : Flow<Resource<List<GetStockListItem>>>
    suspend fun getWorstPerformingStocks() : Flow<Resource<List<GetStockListItem>>>
    suspend fun getActivePerformingStocks() : Flow<Resource<List<GetStockListItem>>>
}