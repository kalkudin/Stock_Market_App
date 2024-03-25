package com.example.finalproject.presentation.state.company_list

import com.example.finalproject.presentation.model.company_list.CompanyList

data class CompanyListState (
    val companyList: List<CompanyList>? = null,
    val originalCompanyList: List<CompanyList>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)