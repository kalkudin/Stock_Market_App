package com.example.finalproject.presentation.event.company_details

sealed class CompanyDetailsEvents {
    data class GetCompanyDetails(val symbol: String) : CompanyDetailsEvents()
    data object BackButtonPressed : CompanyDetailsEvents()
}