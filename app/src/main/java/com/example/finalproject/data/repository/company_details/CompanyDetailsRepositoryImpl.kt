package com.example.finalproject.data.repository.company_details

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.datasource.company_details.RemoteCompanyDetailsDataSource
import com.example.finalproject.domain.model.company_details.CompanyDetails
import com.example.finalproject.domain.repository.company_details.CompanyDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompanyDetailsRepositoryImpl @Inject constructor(
    private val remoteCompanyDetailsDataSource: RemoteCompanyDetailsDataSource,
) : CompanyDetailsRepository {

    //later functions will be added to store items locally and fetch data from local storage
    override suspend fun getCompanyDetailsRemotely(symbol:String): Flow<Resource<List<CompanyDetails>>> {
        return remoteCompanyDetailsDataSource.getCompanyDetailsRemotely(symbol)
    }
}