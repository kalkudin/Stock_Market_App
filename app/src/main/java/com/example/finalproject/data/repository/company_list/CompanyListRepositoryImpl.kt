package com.example.finalproject.data.repository.company_list

import com.example.finalproject.data.common.HandleResponse
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.base.mapToDomain
import com.example.finalproject.data.remote.mapper.company_list.toDomain
import com.example.finalproject.data.remote.service.company_list.CompanyListApiService
import com.example.finalproject.domain.model.company_list.GetCompanyList
import com.example.finalproject.domain.repository.company_list.CompanyListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class CompanyListRepositoryImpl @Inject constructor(
    @Named("MOCKYRetrofit")
    private val apiService: CompanyListApiService,
    private val responseHandler: HandleResponse
) : CompanyListRepository {
    override suspend fun getCompanyListRemotely(): Flow<Resource<List<GetCompanyList>>> {
        return responseHandler.safeApiCall {
            apiService.getCompanyList()
        }.mapToDomain { companyListDto ->
            companyListDto.map { it.toDomain() }
        }
    }
}