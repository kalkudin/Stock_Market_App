package com.example.finalproject.data.remote.service.company_details_chart


import com.example.finalproject.BuildConfig
import com.example.finalproject.data.remote.model.company_details_chart.CompanyChartIntradayDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CompanyChartIntradayApiService {
    @GET("/api/v3/historical-chart/{interval}/{symbol}")
    suspend fun getCompanyChartIntraday(
        @Path("interval") interval: String,
        @Path("symbol") symbol: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("apikey") apikey: String = BuildConfig.API_KEY
    ): Response<List<CompanyChartIntradayDto>>
}