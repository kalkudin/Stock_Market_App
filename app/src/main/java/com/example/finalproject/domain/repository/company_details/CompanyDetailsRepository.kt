package com.example.finalproject.domain.repository.company_details

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.company_details.CompanyDetails
import kotlinx.coroutines.flow.Flow

interface CompanyDetailsRepository {
    suspend fun getCompanyDetailsRemotely(symbol:String): Flow<Resource<List<CompanyDetails>>>
}