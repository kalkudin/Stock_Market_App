package com.example.finalproject.presentation.stock_feature.company_details.event

sealed class CompanyDetailsEvents {
    data class GetCompanyDetails(val symbol: String) : CompanyDetailsEvents()
    data object BackButtonPressed : CompanyDetailsEvents()
}