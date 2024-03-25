package com.example.finalproject.data.remote.model.company_details

data class CompanyDetailsDto(
    val symbol: String,
    val price: Float?,
    val changes: Float?,
    val companyName: String,
    val description: String,
    val image: String
)