package com.example.finalproject.data.repository.company_list

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.datasource.company_list.RemoteCompanyListDataSource
import com.example.finalproject.domain.model.company_list.CompanyList
import com.example.finalproject.domain.repository.company_list.CompanyListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompanyListRepositoryImpl @Inject constructor(
    private val remoteCompanyListDataSource: RemoteCompanyListDataSource,
) : CompanyListRepository {

    //later functions will be added to store items locally and fetch data from local storage
    override suspend fun getCompanyListRemotely() : Flow<Resource<List<CompanyList>>> {
        return remoteCompanyListDataSource.getCompanyListRemotely()
    }
}