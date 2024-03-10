package com.example.finalproject.domain.repository.stock_news

import androidx.paging.PagingData
import com.example.finalproject.domain.model.stock_news.News
import com.example.finalproject.domain.model.stock_news.StockNews
import kotlinx.coroutines.flow.Flow

interface StockNewsRepository {
    suspend fun getStockNews(): Flow<PagingData<News>>
}