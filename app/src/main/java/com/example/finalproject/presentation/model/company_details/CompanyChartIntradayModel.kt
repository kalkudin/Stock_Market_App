package com.example.finalproject.presentation.model.company_details

data class CompanyChartIntradayModel(
    val date: String,
    val open: Float,
    val low: Float,
    val high: Float,
    val close: Float,
    val volume: Int
)