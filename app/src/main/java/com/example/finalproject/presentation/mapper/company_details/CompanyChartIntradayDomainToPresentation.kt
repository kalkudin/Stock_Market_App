package com.example.finalproject.presentation.mapper.company_details

import com.example.finalproject.domain.model.company_details_chart.GetCompanyChartIntraday
import com.example.finalproject.presentation.model.company_details.CompanyChartIntraday

fun GetCompanyChartIntraday.toPresentation(): CompanyChartIntraday {
    return CompanyChartIntraday(
        date = date,
        open = open,
        high = high,
        low = low,
        close = close,
        volume = volume
    )
}