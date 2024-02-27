package com.example.finalproject.data.remote.mapper.company_list

import com.example.finalproject.data.remote.model.company_list.CompanyListDto
import com.example.finalproject.domain.model.company_list.CompanyList

fun CompanyListDto.toDomain(): CompanyList {
    return CompanyList(
        symbol = symbol,
        name = name,
        price = price,
        priceChange = priceChange,
        percentageChange = percentageChange,
        exchangeShortName = exchangeShortName,
        type = type
    )
}