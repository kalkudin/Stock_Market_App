package com.example.finalproject.data.repository.stocks_to_watch

import android.util.Log
import com.example.finalproject.data.common.HandleResponse
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.base.mapToDomain
import com.example.finalproject.data.remote.mapper.stocks_to_watch.toDomain
import com.example.finalproject.data.remote.service.stocks_to_watch.StocksToWatchApiService
import com.example.finalproject.domain.model.stocks_to_watch.StockListItem
import com.example.finalproject.domain.repository.stocks_to_watch.StocksToWatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StocksToWatchRepositoryImpl @Inject constructor(
    private val stocksToWatchApiService: StocksToWatchApiService,
    private val handleResponse: HandleResponse
) : StocksToWatchRepository{

    override suspend fun getBestPerformingStocks(): Flow<Resource<List<StockListItem>>> {
        Log.d("StockRepo", "Best Performing called")
        return handleResponse.safeApiCall { stocksToWatchApiService.getBestStocks() }.mapToDomain {
            stockListDto ->  stockListDto.map { it.toDomain() }
        }
    }

    override suspend fun getWorstPerformingStocks(): Flow<Resource<List<StockListItem>>> {
        Log.d("StockRepo", "Best Performing called")
        return handleResponse.safeApiCall { stocksToWatchApiService.getWorstStocks() }.mapToDomain {
                stockListDto ->  stockListDto.map { it.toDomain() }
        }
    }

    override suspend fun getActivePerformingStocks(): Flow<Resource<List<StockListItem>>> {
        Log.d("StockRepo", "Best Performing called")
        return handleResponse.safeApiCall { stocksToWatchApiService.getActiveStocks() }.mapToDomain {
                stockListDto ->  stockListDto.map { it.toDomain() }
        }
    }
}