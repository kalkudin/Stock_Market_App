package com.example.finalproject.presentation.stock_feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.StocksToWatchUseCases
import com.example.finalproject.presentation.stock_feature.home.event.StockHomeEvent
import com.example.finalproject.presentation.stock_feature.home.mapper.toPresentation
import com.example.finalproject.presentation.stock_feature.home.model.Stock
import com.example.finalproject.presentation.stock_feature.home.state.StockListState
import com.example.finalproject.presentation.util.getErrorMessage
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
            var errorMessage: String? = null

            val fetchJobs = listOf(
                launch {
                    stocksToWatchUseCases.getBestPerformingStocksUseCase().collect { result ->
                        when (result) {
                            is Resource.Success -> bestStocks.addAll(result.data.map {
                                it.toPresentation(
                                    Stock.PerformingType.BEST_PERFORMING
                                )
                            }.take(5))

                            is Resource.Error -> errorMessage =
                                errorMessage ?: getErrorMessage(result.errorType)

                            else -> {}
                        }
                    }
                },
                launch {
                    stocksToWatchUseCases.getWorstPerformingStocksUseCase().collect { result ->
                        when (result) {
                            is Resource.Success -> worstStocks.addAll(result.data.map {
                                it.toPresentation(
                                    Stock.PerformingType.WORST_PERFORMING
                                )
                            }.take(5))

                            is Resource.Error -> errorMessage =
                                errorMessage ?: getErrorMessage(result.errorType)

                            else -> {}
                        }
                    }
                },
                launch {
                    stocksToWatchUseCases.getActivePerformingStocksUseCase().collect { result ->
                        when (result) {
                            is Resource.Success -> activeStocks.addAll(result.data.map {
                                it.toPresentation(
                                    Stock.PerformingType.ACTIVE_PERFORMING
                                )
                            }.take(5))

                            is Resource.Error -> errorMessage =
                                errorMessage ?: getErrorMessage(result.errorType)

                            else -> {}
                        }
                    }
                }
            )

            fetchJobs.joinAll()

            _stockState.update { currentState ->
                Log.d("ViewModel", "Updating state: isLoading = false")
                if (errorMessage != null) {
                    currentState.copy(errorMessage = errorMessage, isLoading = false)
                } else {
                    currentState.copy(
                        bestPerformingStocks = bestStocks,
                        worstPerformingStocks = worstStocks,
                        activePerformingStocks = activeStocks,
                        isLoading = false
                    )
                }
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