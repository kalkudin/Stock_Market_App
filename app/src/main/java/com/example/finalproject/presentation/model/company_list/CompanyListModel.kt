package com.example.finalproject.presentation.model.company_list

data class CompanyListModel (
    val symbol: String,
    val name: String,
    val price:Float,
    val priceChange: Float,
    val percentageChange: Float,
    val exchangeShortName: String,
    val type: String
)