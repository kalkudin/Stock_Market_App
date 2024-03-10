package com.example.finalproject.presentation.event.company_details

import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel
import com.example.finalproject.presentation.model.company_details.UserIdModel

sealed class CompanyDetailsEvents {
    data class GetCompanyDetails(val symbol: String) : CompanyDetailsEvents()

    data class GetCompanyChartIntraday(
        val interval: String,
        val symbol: String,
        val from: String,
        val to: String
    ) : CompanyDetailsEvents()

    data class InsertStocks(val stock: CompanyDetailsModel) : CompanyDetailsEvents()
    data class InsertUser(val user: UserIdModel) : CompanyDetailsEvents()

    data class InsertStocksToWatchlist(val stock: CompanyDetailsModel, val user: UserIdModel) : CompanyDetailsEvents()
    data class DeleteWatchlistedStocks(val stock: CompanyDetailsModel, val user: UserIdModel) : CompanyDetailsEvents()
}