package com.example.finalproject.presentation.screen.stock_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.finalproject.domain.usecase.stock_news.GetStockNewsUseCase
import com.example.finalproject.presentation.event.stock_news.StockNewsEvent
import com.example.finalproject.presentation.mapper.stock_news.toPresentation
import com.example.finalproject.presentation.model.stock_news.News
import com.example.finalproject.presentation.state.stock_news.StockNewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
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

    private val _navigateToUrl = MutableSharedFlow<String>()
    val navigateToUrl: SharedFlow<String> get() = _navigateToUrl

    fun onEvent(event: StockNewsEvent) {
        when (event) {
            is StockNewsEvent.GetNewsList -> getStockNews()
            is StockNewsEvent.NewsItemClick -> onNewsItemClick(event.item)//finish this one later
        }
    }

    private fun onNewsItemClick(item: News) {
        viewModelScope.launch {
            _navigateToUrl.emit(item.link)
        }
    }

    private fun getStockNews() {
        viewModelScope.launch {
            getStockNewsUseCase.invoke().collect { pagingData ->
                _stockNewsState.update { currentState ->
                    currentState.copy(stockNews = pagingData.map { it.toPresentation() })
                }
            }
        }
    }
}