package com.example.finalproject.presentation.stock_feature.company_list.event

import com.example.finalproject.presentation.stock_feature.company_list.model.CompanyListModel

sealed class CompanyListEvents {
    data object GetCompanyList : CompanyListEvents()
    data class CompanyItemClick(val company: CompanyListModel) : CompanyListEvents()
}