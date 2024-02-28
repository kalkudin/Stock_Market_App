package com.example.finalproject.presentation.stock_feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.UserFunds
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.StocksToWatchUseCases
import com.example.finalproject.domain.usecase.UserFundsUseCases
import com.example.finalproject.presentation.stock_feature.home.event.StockHomeEvent
import com.example.finalproject.presentation.stock_feature.home.mapper.formatUserFunds
import com.example.finalproject.presentation.stock_feature.home.mapper.handleResponse
import com.example.finalproject.presentation.stock_feature.home.mapper.handleUserFundsResult
import com.example.finalproject.presentation.stock_feature.home.model.Stock
import com.example.finalproject.presentation.stock_feature.home.state.StockListState
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockHomeViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases,
    private val userFundsUseCases: UserFundsUseCases,
    private val stocksToWatchUseCases: StocksToWatchUseCases,
) : ViewModel() {

    private val _stockState = MutableStateFlow(StockListState())
    val stockState: StateFlow<StockListState> = _stockState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<StockHomeNavigationEvent>()
    val navigationFlow: SharedFlow<StockHomeNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event: StockHomeEvent) {
        when (event) {
            is StockHomeEvent.LogOut -> logOut()
            is StockHomeEvent.GetStocksAndUserData -> getStocksAndUserData()
            is StockHomeEvent.NavigateToStockDetailsPage -> goToDetailsPage(stock = event.stock)
            is StockHomeEvent.NavigateToExtensiveListPage -> goToExtensiveListPage(stockType = event.stockType)
            is StockHomeEvent.NavigateToAddFundsFragment -> goToFundsPage()
        }
    }

    private fun getStocksAndUserData() {
        viewModelScope.launch {
            _stockState.update { it.copy(isLoading = true) }
            val userId = dataStoreUseCases.readUserUidUseCase().first()
            val bestStocks = mutableListOf<Stock>()
            val worstStocks = mutableListOf<Stock>()
            val activeStocks = mutableListOf<Stock>()
            val errorMessages: MutableList<String> = mutableListOf()



            coroutineScope {
                userFundsUseCases.retrieveUserFundsUseCase(userId).collect { result ->
                    handleUserFundsResult(result, _stockState, ::formatUserFunds, ::getErrorMessage) }

                val fetchBest = async {
                    handleResponse(stocksToWatchUseCases.getBestPerformingStocksUseCase(), Stock.PerformingType.BEST_PERFORMING, bestStocks, errorMessages) }
                val fetchWorst = async {
                    handleResponse(stocksToWatchUseCases.getWorstPerformingStocksUseCase(), Stock.PerformingType.WORST_PERFORMING, worstStocks, errorMessages) }
                val fetchActive = async {
                    handleResponse(stocksToWatchUseCases.getActivePerformingStocksUseCase(), Stock.PerformingType.ACTIVE_PERFORMING, activeStocks, errorMessages) }

                awaitAll(fetchBest, fetchWorst, fetchActive)

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

    private fun goToFundsPage() {
        viewModelScope.launch {
            _navigationFlow.emit(StockHomeNavigationEvent.NavigateToFundsPage)
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
    data object NavigateToFundsPage : StockHomeNavigationEvent()
    data class NavigateToExtendedStockList(val stockType: Stock.PerformingType) : StockHomeNavigationEvent()
    data class NavigateToDetailsPage(val stock: Stock) : StockHomeNavigationEvent()
}