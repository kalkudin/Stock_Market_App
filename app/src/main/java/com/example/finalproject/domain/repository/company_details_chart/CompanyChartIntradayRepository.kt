package com.example.finalproject.domain.repository.company_details_chart

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.datasource.company_details_chart.RemoteCompanyChartIntradayDataSource
import com.example.finalproject.domain.model.company_details_chart.CompanyChartIntraday
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CompanyChartIntradayRepository {
    suspend fun getCompanyChartIntradayRemotely(
        interval: String,
        symbol: String,
        from: String,
        to: String
    ) : Flow<Resource<List<CompanyChartIntraday>>>
}