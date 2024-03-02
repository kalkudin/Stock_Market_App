package com.example.finalproject.presentation.stock_feature.stock_news.state

import androidx.paging.PagingData
import com.example.finalproject.presentation.stock_feature.stock_news.model.StockNewsModel

data class StockNewsState(
    val stockNews: PagingData<StockNewsModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)