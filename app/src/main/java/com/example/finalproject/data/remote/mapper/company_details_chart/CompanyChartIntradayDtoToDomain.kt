package com.example.finalproject.data.remote.mapper.company_details_chart

import com.example.finalproject.data.remote.model.company_details_chart.CompanyChartIntradayDto
import com.example.finalproject.domain.model.company_details_chart.GetCompanyChartIntraday

fun CompanyChartIntradayDto.toDomain(): GetCompanyChartIntraday {
    return GetCompanyChartIntraday(
        date = date,
        open = open,
        low = low,
        high = high,
        close = close,
        volume = volume
    )
}