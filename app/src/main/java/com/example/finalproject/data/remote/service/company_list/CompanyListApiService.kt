package com.example.finalproject.data.remote.service.company_list

import com.example.finalproject.data.remote.model.company_list.CompanyListDto
import retrofit2.Response
import retrofit2.http.GET

interface CompanyListApiService {
    @GET("ed1af8aa-9319-44a0-ad15-24bfbe81ab2f")
    suspend fun getCompanyList(): Response<List<CompanyListDto>>
}