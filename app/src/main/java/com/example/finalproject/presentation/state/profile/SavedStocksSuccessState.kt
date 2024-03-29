package com.example.finalproject.presentation.state.profile

import com.example.finalproject.presentation.model.company_details.CompanyDetails

data class SavedStocksSuccessState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val success : Boolean = false,
    val companyDetails : List<CompanyDetails>? = null
)