package com.example.finalproject.presentation.stock_feature.company_details.mapper

import com.example.finalproject.domain.model.company_details.CompanyDetails
import com.example.finalproject.presentation.stock_feature.company_details.model.CompanyDetailsModel

fun CompanyDetails.toPresentation():CompanyDetailsModel{
    return CompanyDetailsModel(
        symbol = symbol,
        companyName = companyName,
        description = description,
        image = image
    )
}