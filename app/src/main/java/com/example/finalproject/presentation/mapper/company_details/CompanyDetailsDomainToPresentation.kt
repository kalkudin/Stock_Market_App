package com.example.finalproject.presentation.mapper.company_details

import com.example.finalproject.domain.model.company_details.GetCompanyDetails
import com.example.finalproject.domain.model.user.GetUserId
import com.example.finalproject.presentation.model.company_details.CompanyDetails
import com.example.finalproject.presentation.model.company_details.UserId

fun GetCompanyDetails.toPresentation(): CompanyDetails {
    return CompanyDetails(
        symbol = symbol,
        price = price,
        changes = changes,
        companyName = companyName,
        description = description,
        image = image
    )
}

fun CompanyDetails.toDomain(): GetCompanyDetails {
    return GetCompanyDetails(
        symbol = symbol,
        price = price,
        changes = changes,
        companyName = companyName,
        description = description,
        image = image
    )
}

fun UserId.toDomain(): GetUserId {
    return GetUserId(
        id = id
    )
}