package com.example.finalproject.presentation.stock_feature.company_list.mapper

import com.example.finalproject.domain.model.company_list.CompanyList
import com.example.finalproject.presentation.stock_feature.company_list.model.CompanyListModel

fun CompanyList.toPresentation(): CompanyListModel {
    return CompanyListModel(
        symbol = symbol,
        name = name,
        price = price,
        priceChange = priceChange,
        percentageChange = percentageChange,
        exchangeShortName = exchangeShortName,
        type = type
    )
}