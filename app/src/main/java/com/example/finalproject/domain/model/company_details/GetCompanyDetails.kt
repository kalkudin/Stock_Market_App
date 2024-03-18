package com.example.finalproject.domain.model.company_details
data class GetCompanyDetails (
    val symbol: String,
    val price: Float?,
    val changes: Float?,
    val companyName: String,
    val description: String,
    val image: String
)