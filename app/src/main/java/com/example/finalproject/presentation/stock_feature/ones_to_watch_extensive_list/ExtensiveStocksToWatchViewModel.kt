package com.example.finalproject.presentation.stock_feature.ones_to_watch_extensive_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.extensive_stocks_usecase.GetExtensiveStocksUseCase
import com.example.finalproject.presentation.stock_feature.home.mapper.toPresentation
import com.example.finalproject.presentation.stock_feature.home.model.Stock
import com.example.finalproject.presentation.stock_feature.ones_to_watch_extensive_list.event.ExtensiveListEvent
import com.example.finalproject.presentation.stock_feature.ones_to_watch_extensive_list.state.ExtensiveStockState
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtensiveStocksToWatchViewModel @Inject constructor(private val getExtensiveStocksUseCase: GetExtensiveStocksUseCase): ViewModel() {

    private val _extensiveStocksFlow = MutableStateFlow(ExtensiveStockState())
    val extensiveStocksFlow : StateFlow<ExtensiveStockState> = _extensiveStocksFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<ExtensiveStocksNavigationEvent>()
    val navigationFlow : SharedFlow<ExtensiveStocksNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event : ExtensiveListEvent) {
        when(event) {
            is ExtensiveListEvent.GetExtensiveStocksList -> getExtensiveStocks(type = event.performingType)
            is ExtensiveListEvent.NavigateToStockHomeFragment -> navigateBack()
            is ExtensiveListEvent.NavigateToStockDetailsPage -> navigateToDetails(stock = event.stock)
        }
    }

    private fun getExtensiveStocks(type: Stock.PerformingType) {
        viewModelScope.launch {
            getExtensiveStocksUseCase(stockType = type).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        val errorMessage = getErrorMessage(resource.errorType)
                        _extensiveStocksFlow.update { currentState ->
                            currentState.copy(
                                errorMessage = errorMessage,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _extensiveStocksFlow.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        val stocksPresentation = resource.data.map { it.toPresentation(type) }
                        _extensiveStocksFlow.update { currentState ->
                            currentState.copy(
                                extensiveStocks = stocksPresentation,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDetails(stock : Stock) {
        viewModelScope.launch {
            _navigationFlow.emit(ExtensiveStocksNavigationEvent.NavigateToDetails(stock = stock))
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _navigationFlow.emit(ExtensiveStocksNavigationEvent.NavigateBack)
        }
    }
}

sealed class ExtensiveStocksNavigationEvent {
    data object NavigateBack : ExtensiveStocksNavigationEvent()
    data class NavigateToDetails(val stock : Stock) : ExtensiveStocksNavigationEvent()
}