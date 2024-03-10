package com.example.finalproject.presentation.mapper.company_details

import com.example.finalproject.domain.model.company_details.CompanyDetails
import com.example.finalproject.domain.model.user.UserId
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel
import com.example.finalproject.presentation.model.company_details.UserIdModel

fun CompanyDetails.toPresentation(): CompanyDetailsModel {
    return CompanyDetailsModel(
        symbol = symbol,
        price = price,
        changes = changes,
        companyName = companyName,
        description = description,
        image = image
    )
}

fun CompanyDetailsModel.toDomain(): CompanyDetails {
    return CompanyDetails(
        symbol = symbol,
        price = price,
        changes = changes,
        companyName = companyName,
        description = description,
        image = image
    )
}

fun UserIdModel.toDomain(): UserId {
    return UserId(
        id = id
    )
}