package com.example.finalproject.presentation.stock_feature.company_details.model

data class CompanyChartIntradayModel(
    val date: String,
    val open: Float,
    val low: Float,
    val high: Float,
    val close: Float,
    val volume: Int
)