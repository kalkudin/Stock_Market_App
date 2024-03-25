package com.example.finalproject.domain.model.company_details_chart

data class GetCompanyChartIntraday(
    val date: String,
    val open: Float,
    val low: Float,
    val high: Float,
    val close: Float,
    val volume: Int
)