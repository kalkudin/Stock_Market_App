package com.example.finalproject.presentation.event.company_list

import com.example.finalproject.presentation.model.company_list.CompanyList

sealed class CompanyListEvent {
    data object GetCompanyList : CompanyListEvent()

    data class ListSearch(val query: String) : CompanyListEvent()
    data class CompanyItemClick(val company: CompanyList) : CompanyListEvent()
}