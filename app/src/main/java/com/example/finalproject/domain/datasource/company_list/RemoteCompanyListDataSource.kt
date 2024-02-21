package com.example.finalproject.domain.datasource.company_list

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.company_list.CompanyList
import kotlinx.coroutines.flow.Flow

interface RemoteCompanyListDataSource {
    suspend fun getCompanyListRemotely(): Flow<Resource<List<CompanyList>>>
}