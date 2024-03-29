package com.example.finalproject.data.remote.service.stock_news

import com.example.finalproject.BuildConfig
import com.example.finalproject.data.remote.model.company_list.CompanyListDto
import com.example.finalproject.data.remote.model.stock_news.StockNewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockNewsApiService {
    @GET("/api/v3/fmp/articles")
    suspend fun getStockNews(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ): Response<StockNewsDto>
}