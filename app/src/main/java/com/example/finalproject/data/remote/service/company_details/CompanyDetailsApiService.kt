package com.example.finalproject.data.remote.service.company_details

import com.example.finalproject.BuildConfig
import com.example.finalproject.data.remote.model.company_details.CompanyDetailsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CompanyDetailsApiService {
    @GET("/api/v3/profile/{symbol}")
    suspend fun getCompanyDetails(
        @Path("symbol") symbol: String,
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ): Response<List<CompanyDetailsDto>>
}