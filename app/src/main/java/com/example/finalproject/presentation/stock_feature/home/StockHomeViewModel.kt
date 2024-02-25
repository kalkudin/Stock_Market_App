package com.example.finalproject.presentation.stock_feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.StocksToWatchUseCases
import com.example.finalproject.presentation.stock_feature.home.event.StockHomeEvent
import com.example.finalproject.presentation.stock_feature.home.model.Stock
import com.example.finalproject.presentation.stock_feature.home.state.StockListState
import com.example.finalproject.presentation.stock_feature.home.mapper.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockHomeViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases,
    private val stocksToWatchUseCases: StocksToWatchUseCases
) : ViewModel() {

    private val _stockState = MutableStateFlow(StockListState())
    val stockState: StateFlow<StockListState> = _stockState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<StockHomeNavigationEvent>()
    val navigationFlow: SharedFlow<StockHomeNavigationEvent> = _navigationFlow.asSharedFlow()
    fun onEvent(event: StockHomeEvent) {
        when (event) {
            is StockHomeEvent.LogOut -> logOut()
            is StockHomeEvent.GetStocksToWatch -> getStocksToWatch()
            is StockHomeEvent.NavigateToStockDetailsPage -> goToDetailsPage(stock = event.stock)
            is StockHomeEvent.NavigateToExtensiveListPage -> goToExtensiveListPage(stockType= event.stockType)
        }
    }

    private fun getStocksToWatch() {
        viewModelScope.launch {
            _stockState.update { it.copy(isLoading = true) }

            val bestStocks = mutableListOf<Stock>()
            val worstStocks = mutableListOf<Stock>()
            val activeStocks = mutableListOf<Stock>()
            val errorMessages: MutableList<String> = mutableListOf()

            val fetchJobs = listOf(
                launch { handleResponse(stocksToWatchUseCases.getBestPerformingStocksUseCase(), Stock.PerformingType.BEST_PERFORMING, bestStocks, errorMessages) },
                launch { handleResponse(stocksToWatchUseCases.getWorstPerformingStocksUseCase(), Stock.PerformingType.WORST_PERFORMING, worstStocks, errorMessages) },
                launch { handleResponse(stocksToWatchUseCases.getActivePerformingStocksUseCase(), Stock.PerformingType.ACTIVE_PERFORMING, activeStocks, errorMessages) }
            )

            fetchJobs.joinAll()

            _stockState.update { currentState ->
                currentState.copy(
                    bestPerformingStocks = bestStocks,
                    worstPerformingStocks = worstStocks,
                    activePerformingStocks = activeStocks,
                    errorMessage = errorMessages.firstOrNull(),
                    isLoading = false
                )
            }
        }
    }

    private fun goToDetailsPage(stock: Stock) {
        viewModelScope.launch {
            _navigationFlow.emit(StockHomeNavigationEvent.NavigateToDetailsPage(stock = stock))
        }
    }

    private fun goToExtensiveListPage(stockType: Stock.PerformingType) {
        viewModelScope.launch {
            _navigationFlow.emit(StockHomeNavigationEvent.NavigateToExtendedStockList(stockType = stockType))
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            dataStoreUseCases.clearUserSessionUseCase()
            _navigationFlow.emit(StockHomeNavigationEvent.LogOut)
        }
    }
}

sealed class StockHomeNavigationEvent {
    data object LogOut : StockHomeNavigationEvent()
    data class NavigateToExtendedStockList(val stockType: Stock.PerformingType) : StockHomeNavigationEvent()
    data class NavigateToDetailsPage(val stock: Stock) : StockHomeNavigationEvent()
}