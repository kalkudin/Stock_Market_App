package com.example.finalproject.presentation.stock_feature.company_details.state

import com.example.finalproject.presentation.stock_feature.company_details.model.CompanyChartIntradayModel
import com.example.finalproject.presentation.stock_feature.company_details.model.CompanyDetailsModel

data class CompanyDetailsState(
    val companyDetails: List<CompanyDetailsModel>? = null,
    val companyChartIntraday: List<CompanyChartIntradayModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)