package com.example.finalproject.domain.usecase.extensive_stocks_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.stocks_to_watch.GetStockListItem
import com.example.finalproject.domain.repository.stocks_to_watch.StocksToWatchRepository
import com.example.finalproject.presentation.model.home.Stock
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExtensiveStocksUseCase @Inject constructor(private val stocksToWatchRepository: StocksToWatchRepository) {
    suspend operator fun invoke(stockType: Stock.PerformingType): Flow<Resource<List<GetStockListItem>>> {
        return when (stockType) {
            Stock.PerformingType.BEST_PERFORMING -> {
                stocksToWatchRepository.getBestPerformingStocks()
            }
            Stock.PerformingType.WORST_PERFORMING -> {
                stocksToWatchRepository.getWorstPerformingStocks()
            }
            Stock.PerformingType.ACTIVE_PERFORMING -> {
                stocksToWatchRepository.getActivePerformingStocks()
            }
        }
    }
}