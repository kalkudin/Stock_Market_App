package com.example.finalproject.domain.datasource.company_details

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.company_details.GetCompanyDetails
import kotlinx.coroutines.flow.Flow

interface RemoteCompanyDetailsDataSource {
    suspend fun getCompanyDetailsRemotely(symbol:String): Flow<Resource<List<GetCompanyDetails>>>
}