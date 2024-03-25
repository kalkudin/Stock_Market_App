package com.example.finalproject.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.AuthUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.StocksToWatchUseCases
import com.example.finalproject.domain.usecase.UserFundsUseCases
import com.example.finalproject.presentation.event.home.StockHomeEvent
import com.example.finalproject.presentation.mapper.home.formatFirstName
import com.example.finalproject.presentation.mapper.base.handleStateUpdate
import com.example.finalproject.presentation.mapper.home.toPresentation
import com.example.finalproject.presentation.model.home.Stock
import com.example.finalproject.presentation.state.home.StockListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class StockHomeViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases,
    private val userFundsUseCases: UserFundsUseCases,
    private val stocksToWatchUseCases: StocksToWatchUseCases,
    private val authUseCases: AuthUseCases
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
            is StockHomeEvent.NavigateToStockNews -> goToStockNews()
        }
    }

    private fun getStocksAndUserData() {
        viewModelScope.launch {
            val userId = dataStoreUseCases.readUserUidUseCase().first()

            _stockState.update { state -> state.copy(isLoading = true) }

            val userInfoJob = async {
                authUseCases.getUserInitialsUseCase(uid = userId).collect { resource ->
                    handleStateUpdate(
                        resource = resource,
                        stateFlow = _stockState,
                        onSuccess = {initials -> this.copy(userFirstName = initials.formatFirstName())},
                        onError = {errorMessage -> this.copy(errorMessage = errorMessage)}
                    )
                }
            }

            val fundsJob = async {
                userFundsUseCases.retrieveUserFundsUseCase(uid = userId).collect { resource ->
                    handleStateUpdate(
                        resource = resource,
                        stateFlow = _stockState,
                        onSuccess = {funds -> this.copy(userFunds = round(funds.amount).toString())},
                        onError = { errorMessage -> this.copy(errorMessage = errorMessage) }
                    )
                }
            }

            val bestStocksJob = async {
                stocksToWatchUseCases.getBestPerformingStocksUseCase().collect { resource ->
                    handleStateUpdate(
                        resource = resource,
                        stateFlow = _stockState,
                        onSuccess = {bestStocks -> this.copy(bestPerformingStocks = bestStocks.take(6).map { stock ->
                            stock.toPresentation(Stock.PerformingType.BEST_PERFORMING) })},
                        onError = { errorMessage -> this.copy(errorMessage = errorMessage) }
                    )
                }
            }

            val worstStocksJob = async {
                stocksToWatchUseCases.getWorstPerformingStocksUseCase().collect { resource ->
                    handleStateUpdate(
                        resource = resource,
                        stateFlow = _stockState,
                        onSuccess = {worstStocks -> this.copy(worstPerformingStocks = worstStocks.take(6).map { stock ->
                            stock.toPresentation(Stock.PerformingType.WORST_PERFORMING) })},
                        onError = { errorMessage -> this.copy(errorMessage = errorMessage) }
                    )
                }
            }

            val activeStocksJob = async {
                stocksToWatchUseCases.getActivePerformingStocksUseCase().collect { resource ->
                    handleStateUpdate(
                        resource = resource,
                        stateFlow = _stockState,
                        onSuccess = {activeStocks -> this.copy(activePerformingStocks = activeStocks.take(6).map { stock ->
                            stock.toPresentation(Stock.PerformingType.ACTIVE_PERFORMING) })},
                        onError = { errorMessage -> this.copy(errorMessage = errorMessage) }
                    )
                }
            }

            awaitAll(fundsJob, bestStocksJob, worstStocksJob, activeStocksJob, userInfoJob)
            _stockState.update { state -> state.copy(isLoading = false)}
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

    private fun goToStockNews() {
        viewModelScope.launch {
            _navigationFlow.emit(StockHomeNavigationEvent.NavigateToStockNews)
        }
    }
}

sealed class StockHomeNavigationEvent {
    data object LogOut : StockHomeNavigationEvent()
    data object NavigateToFundsPage : StockHomeNavigationEvent()
    data object NavigateToStockNews : StockHomeNavigationEvent()
    data class NavigateToDetailsPage(val stock: Stock) : StockHomeNavigationEvent()
    data class NavigateToExtendedStockList(val stockType: Stock.PerformingType) : StockHomeNavigationEvent()
}