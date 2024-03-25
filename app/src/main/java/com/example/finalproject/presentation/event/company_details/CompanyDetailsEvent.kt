package com.example.finalproject.presentation.event.company_details

import com.example.finalproject.presentation.model.company_details.CompanyDetails
import com.example.finalproject.presentation.model.company_details.UserId

sealed class CompanyDetailsEvent {
    data class GetCompanyDetails(val symbol: String) : CompanyDetailsEvent()

    data class GetCompanyChartIntraday(
        val interval: String,
        val symbol: String,
        val from: String,
        val to: String
    ) : CompanyDetailsEvent()

    data class InsertStocks(val stock: CompanyDetails) : CompanyDetailsEvent()
    data class InsertUser(val user: UserId) : CompanyDetailsEvent()

    data class InsertStocksToWatchlist(val stock: CompanyDetails, val user: UserId) : CompanyDetailsEvent()
    data class DeleteWatchlistedStocks(val stock: CompanyDetails, val user: UserId) : CompanyDetailsEvent()
    data class BuyStock(val userId: String, val amount: Double, val description: String) : CompanyDetailsEvent()
    data class SellStock(val userId: String, val amount: Double, val description: String) : CompanyDetailsEvent()
    data class IsStockInWatchlist(val userId: String, val symbol: String) : CompanyDetailsEvent()
    data object ResetFlow : CompanyDetailsEvent()
}