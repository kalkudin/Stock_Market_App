package com.example.finalproject.presentation.state.stock_news

import androidx.paging.PagingData
import com.example.finalproject.presentation.model.stock_news.News

data class StockNewsState(
    val stockNews: PagingData<News>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)
