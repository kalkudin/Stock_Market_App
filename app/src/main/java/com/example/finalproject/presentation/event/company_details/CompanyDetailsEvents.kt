package com.example.finalproject.presentation.event.company_details

sealed class CompanyDetailsEvents {
    data class GetCompanyDetails(val symbol: String) : CompanyDetailsEvents()

    data class GetCompanyChartIntraday(
        val interval: String,
        val symbol: String,
        val from: String,
        val to: String
    ) : CompanyDetailsEvents()
}