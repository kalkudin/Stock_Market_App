package com.example.finalproject.domain.repository.company_list

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.company_list.CompanyList
import kotlinx.coroutines.flow.Flow

interface CompanyListRepository {
    suspend fun getCompanyListRemotely(): Flow<Resource<List<CompanyList>>>

}