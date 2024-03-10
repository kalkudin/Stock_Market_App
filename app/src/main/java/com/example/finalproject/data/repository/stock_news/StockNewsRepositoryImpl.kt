package com.example.finalproject.data.repository.stock_news

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.finalproject.data.remote.mapper.stock_news.toDomain
import com.example.finalproject.data.remote.paging.stock_news.StockNewsPagingSource
import com.example.finalproject.data.remote.service.stock_news.StockNewsApiService
import com.example.finalproject.domain.model.stock_news.News
import com.example.finalproject.domain.repository.stock_news.StockNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StockNewsRepositoryImpl @Inject constructor(
    private val apiService: StockNewsApiService
) : StockNewsRepository {
    override suspend fun getStockNews(): Flow<PagingData<News>> {
        return Pager(PagingConfig(pageSize = 10)) {
            StockNewsPagingSource(apiService)
        }.flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}