package com.example.finalproject.presentation.state.company_list

import com.example.finalproject.presentation.model.company_list.CompanyListModel

data class CompanyListState (
    val companyList: List<CompanyListModel>? = null,
    val originalCompanyList: List<CompanyListModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)