package com.example.finalproject.data.remote.paging.stock_news

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.finalproject.data.remote.model.stock_news.NewsDto
import com.example.finalproject.data.remote.model.stock_news.StockNewsDto
import com.example.finalproject.data.remote.service.stock_news.StockNewsApiService
import retrofit2.HttpException
import java.io.IOException

class StockNewsPagingSource(
    private val apiService: StockNewsApiService
) : PagingSource<Int, NewsDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsDto> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getStockNews(page = page, size = 10)
            if (response.isSuccessful) {
                val data = response.body()?.content ?: emptyList()
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

    override fun getRefreshKey(state: PagingState<Int, NewsDto>): Int? {
        return state.anchorPosition
    }
}