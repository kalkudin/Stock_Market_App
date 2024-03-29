package com.example.finalproject.domain.repository.company_details_chart

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.company_details_chart.GetCompanyChartIntraday
import kotlinx.coroutines.flow.Flow

interface CompanyChartIntradayRepository {
    suspend fun getCompanyChartIntradayRemotely(
        interval: String,
        symbol: String,
        from: String,
        to: String
    ) : Flow<Resource<List<GetCompanyChartIntraday>>>
}