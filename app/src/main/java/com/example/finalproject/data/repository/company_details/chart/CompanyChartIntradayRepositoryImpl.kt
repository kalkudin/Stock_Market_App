package com.example.finalproject.data.repository.company_details.chart

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.datasource.company_details_chart.RemoteCompanyChartIntradayDataSource
import com.example.finalproject.domain.model.company_details_chart.GetCompanyChartIntraday
import com.example.finalproject.domain.repository.company_details_chart.CompanyChartIntradayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompanyChartIntradayRepositoryImpl @Inject constructor(
    private val remoteCompanyChartIntradayDataSource: RemoteCompanyChartIntradayDataSource
) : CompanyChartIntradayRepository {
    override suspend fun getCompanyChartIntradayRemotely(
        interval: String,
        symbol: String,
        from: String,
        to: String
    ): Flow<Resource<List<GetCompanyChartIntraday>>> {
        return remoteCompanyChartIntradayDataSource.getCompanyChartIntradayRemotely(
            interval = interval,
            symbol = symbol,
            from = from,
            to = to
        )
    }
}