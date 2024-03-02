package com.example.finalproject.presentation.stock_feature.stock_news.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.domain.usecase.stock_news_usecase.GetStockNewsUseCase
import com.example.finalproject.presentation.stock_feature.stock_news.event.StockNewsEvents
import com.example.finalproject.presentation.stock_feature.stock_news.mapper.toPresentation
import com.example.finalproject.presentation.stock_feature.stock_news.state.StockNewsState
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockNewsViewModel @Inject constructor(
    private val getStockNewsUseCase: GetStockNewsUseCase
) : ViewModel() {

    private val _stockNewsState = MutableStateFlow(StockNewsState())
    val stockNewsState: SharedFlow<StockNewsState> get() = _stockNewsState

    fun onEvent(event: StockNewsEvents) {
        Log.d("StockNewsViewModel", "onEvent called with event: $event")
        when (event) {
            is StockNewsEvents.GetStockNews -> getStockNews(event.page)
            is StockNewsEvents.NavigateToStockNewsDetail -> navigateToDetail(event.url)
        }
    }

    private fun navigateToDetail(url: String) {
        Log.d("StockNewsViewModel", "navigateToDetail called with url: $url")
    }

    private fun getStockNews(page: Int) {
        Log.d("StockNewsViewModel", "getStockNews called with page: $page")
        viewModelScope.launch {
            getStockNewsUseCase.invoke(page).collect { pagingData ->
                _stockNewsState.update { currentState ->
                    currentState.copy(stockNews = pagingData.map { it.toPresentation() })
                }
            }
        }
    }

    private fun updateErrorMessages(errorMessage: ErrorType) {
        Log.d("StockNewsViewModel", "updateErrorMessages called with errorMessage: $errorMessage")
        val message = getErrorMessage(errorMessage)
        _stockNewsState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }
}