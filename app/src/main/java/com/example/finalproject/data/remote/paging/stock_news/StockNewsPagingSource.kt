package com.example.finalproject.data.remote.paging.stock_news

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.finalproject.data.remote.model.stock_news.StockNewsDto
import com.example.finalproject.data.remote.service.stock_news.StockNewsApiService
import kotlinx.coroutines.flow.single
import retrofit2.HttpException
import java.io.IOException

class StockNewsPagingSource(
    private val apiService: StockNewsApiService
) : PagingSource<Int, StockNewsDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StockNewsDto> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getStockNews(page = page, size = 10).single()
            if (response.isSuccessful) {
                val data = response.body() ?: emptyList()
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StockNewsDto>): Int? {
        return state.anchorPosition
    }
}