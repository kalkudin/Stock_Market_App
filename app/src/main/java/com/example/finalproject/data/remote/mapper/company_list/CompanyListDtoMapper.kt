package com.example.finalproject.data.remote.mapper.company_list

import com.example.finalproject.data.remote.model.company_list.CompanyListDto
import com.example.finalproject.domain.model.company_list.CompanyList

fun CompanyListDto.toDomain(): CompanyList {
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