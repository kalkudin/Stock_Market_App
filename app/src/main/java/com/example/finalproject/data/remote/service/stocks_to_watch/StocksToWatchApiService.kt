package com.example.finalproject.data.remote.service.stocks_to_watch

import com.example.finalproject.BuildConfig
import com.example.finalproject.data.remote.model.stocks_to_watch.StockListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StocksToWatchApiService {
    @GET("/api/v3/stock_market/gainers")
    suspend fun getBestStocks(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ) : Response<List<StockListDto>>

    @GET("/api/v3/stock_market/losers")
    suspend fun getWorstStocks(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ) : Response<List<StockListDto>>

    @GET("/api/v3/stock_market/actives")
    suspend fun getActiveStocks(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ) : Response<List<StockListDto>>
}