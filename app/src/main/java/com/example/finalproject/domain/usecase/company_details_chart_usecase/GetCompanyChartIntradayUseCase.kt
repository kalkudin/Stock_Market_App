package com.example.finalproject.domain.usecase.company_details_chart_usecase

import com.example.finalproject.domain.repository.company_details_chart.CompanyChartIntradayRepository
import javax.inject.Inject

class GetCompanyChartIntradayUseCase @Inject constructor(
    private val companyChartIntradayRepository: CompanyChartIntradayRepository
) {
    suspend operator fun invoke(
        interval: String,
        symbol: String,
        from: String,
        to: String
    ) = companyChartIntradayRepository.getCompanyChartIntradayRemotely(
        interval = interval,
        symbol = symbol,
        from = from,
        to = to
    )
}