package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.usecase.stocks_to_watch_usecase.GetActivePerformingStocksUseCase
import com.example.finalproject.domain.usecase.stocks_to_watch_usecase.GetBestPerformingStocksUseCase
import com.example.finalproject.domain.usecase.stocks_to_watch_usecase.GetWorstPerformingStocksUseCase
import javax.inject.Inject

data class StocksToWatchUseCases @Inject constructor(
    val getActivePerformingStocksUseCase: GetActivePerformingStocksUseCase,
    val getBestPerformingStocksUseCase: GetBestPerformingStocksUseCase,
    val getWorstPerformingStocksUseCase: GetWorstPerformingStocksUseCase
)