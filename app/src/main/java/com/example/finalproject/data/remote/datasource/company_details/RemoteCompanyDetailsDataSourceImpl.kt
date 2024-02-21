package com.example.finalproject.data.remote.datasource.company_details

import com.example.finalproject.data.common.HandleResponse
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.base.mapToDomain
import com.example.finalproject.data.remote.mapper.company_details.toDomain
import com.example.finalproject.data.remote.service.company_details.CompanyDetailsApiService
import com.example.finalproject.domain.datasource.company_details.RemoteCompanyDetailsDataSource
import com.example.finalproject.domain.model.company_details.CompanyDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class RemoteCompanyDetailsDataSourceImpl @Inject constructor(
    @Named("FMPRetrofit")
    private val apiService: CompanyDetailsApiService,
    private val responseHandler: HandleResponse
) : RemoteCompanyDetailsDataSource {
    override suspend fun getCompanyDetailsRemotely(symbol:String): Flow<Resource<List<CompanyDetails>>> {
        return responseHandler.safeApiCall {
            apiService.getCompanyDetails(symbol)
        }.mapToDomain { companyDetailsDto ->
            companyDetailsDto.map { it.toDomain() }
        }
    }
}