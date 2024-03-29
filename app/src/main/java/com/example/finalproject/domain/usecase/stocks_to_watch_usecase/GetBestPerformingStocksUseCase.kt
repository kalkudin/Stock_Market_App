package com.example.finalproject.domain.usecase.stocks_to_watch_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.stocks_to_watch.GetStockListItem
import com.example.finalproject.domain.repository.stocks_to_watch.StocksToWatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBestPerformingStocksUseCase @Inject constructor(private val stocksToWatchRepository: StocksToWatchRepository) {
    suspend operator fun invoke() : Flow<Resource<List<GetStockListItem>>> {
        return stocksToWatchRepository.getBestPerformingStocks()
    }
}