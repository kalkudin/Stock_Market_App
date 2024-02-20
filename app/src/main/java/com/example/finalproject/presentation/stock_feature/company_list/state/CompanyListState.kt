package com.example.finalproject.presentation.stock_feature.company_list.state

import androidx.paging.PagingData
import com.example.finalproject.presentation.stock_feature.company_list.model.CompanyListModel

data class CompanyListState (
    val companyList: List<CompanyListModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)