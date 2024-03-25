package com.example.finalproject.data.repository.company_details.chart

import com.example.finalproject.data.common.HandleResponse
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.remote.mapper.base.mapToDomain
import com.example.finalproject.data.remote.mapper.company_details_chart.toDomain
import com.example.finalproject.data.remote.service.company_details_chart.CompanyChartIntradayApiService
import com.example.finalproject.domain.model.company_details_chart.GetCompanyChartIntraday
import com.example.finalproject.domain.repository.company_details_chart.CompanyChartIntradayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompanyChartIntradayRepositoryImpl @Inject constructor(
    private val apiService: CompanyChartIntradayApiService,
    private val responseHandler: HandleResponse
) : CompanyChartIntradayRepository {
    override suspend fun getCompanyChartIntradayRemotely(
        interval: String,
        symbol: String,
        from: String,
        to: String
    ): Flow<Resource<List<GetCompanyChartIntraday>>> {
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