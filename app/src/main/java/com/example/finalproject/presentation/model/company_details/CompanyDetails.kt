package com.example.finalproject.presentation.model.company_details

data class CompanyDetails(
    val symbol: String,
    val price: Float?,
    val changes: Float?,
    val companyName: String,
    val description: String,
    val image: String,
    var isFavorite: Boolean = false
)