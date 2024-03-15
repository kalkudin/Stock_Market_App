package com.example.finalproject.presentation.state.company_details

import com.example.finalproject.presentation.model.company_details.CompanyChartIntradayModel
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel

data class CompanyDetailsState(
    val companyDetails: List<CompanyDetailsModel>? = null,
    val companyChartIntraday: List<CompanyChartIntradayModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isStockInWatchlist: Boolean = false
)