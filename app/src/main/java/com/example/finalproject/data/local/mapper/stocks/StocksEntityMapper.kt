package com.example.finalproject.data.local.mapper.stocks

import com.example.finalproject.data.local.model.stocks.StocksEntity
import com.example.finalproject.domain.model.company_details.GetCompanyDetails

fun StocksEntity.toDomain(): GetCompanyDetails {
    return GetCompanyDetails(
        symbol = symbol,
        price = price,
        changes = changes,
        companyName = companyName,
        description = description,
        image = image

    )
}

fun GetCompanyDetails.toData(): StocksEntity {
    return StocksEntity(
        symbol = symbol,
        price = price,
        changes = changes,
        companyName = companyName,
        description = description,
        image = image
    )
}