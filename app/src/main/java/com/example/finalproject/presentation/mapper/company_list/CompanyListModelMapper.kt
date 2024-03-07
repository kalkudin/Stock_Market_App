package com.example.finalproject.presentation.mapper.company_list

import com.example.finalproject.domain.model.company_list.CompanyList
import com.example.finalproject.presentation.model.company_list.CompanyListModel

fun CompanyList.toPresentation(): CompanyListModel {
    return CompanyListModel(
        symbol = symbol,
        name = name,
        price = price,
        exchangeShortName = exchangeShortName,
        type = type
    )
}