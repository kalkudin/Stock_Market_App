package com.example.finalproject.presentation.state.stock_news

import androidx.paging.PagingData
import com.example.finalproject.presentation.model.stock_news.NewsModel
import com.example.finalproject.presentation.model.stock_news.StockNewsModel

data class StockNewsState(
    val stockNews: PagingData<NewsModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)
