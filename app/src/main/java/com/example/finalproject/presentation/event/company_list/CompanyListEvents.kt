package com.example.finalproject.presentation.event.company_list

import com.example.finalproject.presentation.model.company_list.CompanyListModel

sealed class CompanyListEvents {
    data object GetCompanyList : CompanyListEvents()

    data class ListSearch(val query: String) : CompanyListEvents()
    data class CompanyItemClick(val company: CompanyListModel) : CompanyListEvents()
}