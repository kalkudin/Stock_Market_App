package com.example.finalproject.presentation.mapper.company_details

import com.example.finalproject.domain.model.company_details.CompanyDetails
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel

fun CompanyDetails.toPresentation(): CompanyDetailsModel {
    return CompanyDetailsModel(
        symbol = symbol,
        companyName = companyName,
        description = description
    )
}