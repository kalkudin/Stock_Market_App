package com.example.finalproject.data.remote.datasource.company_details_chart

import com.example.finalproject.data.common.HandleResponse
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.base.mapToDomain
import com.example.finalproject.data.remote.mapper.company_details_chart.toDomain
import com.example.finalproject.data.remote.service.company_details_chart.CompanyChartIntradayApiService
import com.example.finalproject.domain.datasource.company_details_chart.RemoteCompanyChartIntradayDataSource
import com.example.finalproject.domain.model.company_details_chart.CompanyChartIntraday
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteCompanyChartIntradayDataSourceImpl @Inject constructor(
    private val apiService: CompanyChartIntradayApiService,
    private val responseHandler: HandleResponse
) : RemoteCompanyChartIntradayDataSource {
    override suspend fun getCompanyChartIntradayRemotely(
        interval: String,
        symbol: String,
        from: String,
        to: String
    ): Flow<Resource<List<CompanyChartIntraday>>> {
        return responseHandler.safeApiCall {
            apiService.getCompanyChartIntraday(
                interval = interval,
                symbol = symbol,
                from = from,
                to = to,
            )
        }.mapToDomain { companyChartIntradayDto ->
            companyChartIntradayDto.map {
                it.toDomain()
            }
        }
    }
}