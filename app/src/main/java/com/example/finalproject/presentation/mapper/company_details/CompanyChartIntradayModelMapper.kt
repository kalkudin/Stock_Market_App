package com.example.finalproject.presentation.mapper.company_details

import com.example.finalproject.domain.model.company_details_chart.CompanyChartIntraday
import com.example.finalproject.presentation.model.company_details.CompanyChartIntradayModel

fun CompanyChartIntraday.toPresentation(): CompanyChartIntradayModel {
    return CompanyChartIntradayModel(
        date = date,
        open = open,
        high = high,
        low = low,
        close = close,
        volume = volume
    )
}