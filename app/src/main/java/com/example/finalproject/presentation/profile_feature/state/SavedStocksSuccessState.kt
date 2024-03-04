package com.example.finalproject.presentation.profile_feature.state

import com.example.finalproject.presentation.stock_feature.company_details.model.CompanyDetailsModel

data class SavedStocksSuccessState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val success : Boolean = false,
    val companyDetails : List<CompanyDetailsModel>? = null
)