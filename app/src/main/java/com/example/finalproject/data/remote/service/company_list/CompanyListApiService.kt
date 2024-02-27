package com.example.finalproject.data.remote.service.company_list

import com.example.finalproject.data.remote.model.company_list.CompanyListDto
import retrofit2.Response
import retrofit2.http.GET

interface CompanyListApiService {
    @GET("91776aaa-ca12-4c57-8330-28ef21085895")
    suspend fun getCompanyList(): Response<List<CompanyListDto>>
}