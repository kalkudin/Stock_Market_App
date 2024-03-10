package com.example.finalproject.data.remote.mapper.company_details

import com.example.finalproject.data.remote.model.company_details.CompanyDetailsDto
import com.example.finalproject.domain.model.company_details.CompanyDetails

fun CompanyDetailsDto.toDomain(): CompanyDetails {
    return CompanyDetails(
        symbol = symbol,
        price = price,
        changes = changes,
        companyName = companyName,
        description = description,
        image = image
    )
}