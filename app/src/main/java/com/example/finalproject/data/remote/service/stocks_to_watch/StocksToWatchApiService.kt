package com.example.finalproject.data.remote.service.stocks_to_watch

import com.example.finalproject.BuildConfig
import com.example.finalproject.data.remote.model.stocks_to_watch.StockListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StocksToWatchApiService {
    @GET("e37f2736-d4be-477e-b7bb-91e2e4a45899")
    suspend fun getBestStocks(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ) : Response<List<StockListDto>>

    @GET("2080748b-0af0-4e88-a1b0-1ee8c01f1e9c")
    suspend fun getWorstStocks(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ) : Response<List<StockListDto>>

    @GET("1f1e5417-2aaa-4fc6-9461-9d94db1d1dd4")
    suspend fun getActiveStocks(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ) : Response<List<StockListDto>>
}