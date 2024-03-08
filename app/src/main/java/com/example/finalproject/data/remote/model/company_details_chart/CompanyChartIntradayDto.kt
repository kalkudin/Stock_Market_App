package com.example.finalproject.data.remote.model.company_details_chart

data class CompanyChartIntradayDto(
    val date: String,
    val open: Float,
    val low: Float,
    val high: Float,
    val close: Float,
    val volume: Int
)