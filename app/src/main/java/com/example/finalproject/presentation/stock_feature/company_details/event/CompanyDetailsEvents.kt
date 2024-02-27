package com.example.finalproject.presentation.stock_feature.company_details.event

sealed class CompanyDetailsEvents {
    data class GetCompanyDetails(val symbol: String) : CompanyDetailsEvents()

    data class GetCompanyChartIntraday(
        val interval: String,
        val symbol: String,
        val from: String,
        val to: String
    ) : CompanyDetailsEvents()

    data object BackButtonPressed : CompanyDetailsEvents()
}