package com.example.finalproject.presentation.mapper.company_list

import com.example.finalproject.domain.model.company_list.GetCompanyList
import com.example.finalproject.presentation.model.company_list.CompanyList

fun GetCompanyList.toPresentation(): CompanyList {
    return CompanyList(
        symbol = symbol,
        name = name,
        price = price,
        exchangeShortName = exchangeShortName,
        type = type,
        priceChange = priceChange,
        percentageChange = percentageChange
    )
}