package com.example.finalproject.presentation.stock_feature.company_details.mapper

import com.example.finalproject.domain.model.company_details_chart.CompanyChartIntraday
import com.example.finalproject.presentation.stock_feature.company_details.model.CompanyChartIntradayModel

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