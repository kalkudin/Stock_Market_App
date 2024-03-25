package com.example.finalproject.presentation.state.company_details

import com.example.finalproject.presentation.model.company_details.CompanyChartIntraday
import com.example.finalproject.presentation.model.company_details.CompanyDetails

data class CompanyDetailsState(
    val companyDetails: List<CompanyDetails>? = null,
    val companyChartIntraday: List<CompanyChartIntraday>? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isLoading: Boolean = false,
    val isStockInWatchlist: Boolean = false
)