package com.example.finalproject.domain.model.company_list

data class GetCompanyList(
    val symbol: String,
    val name: String,
    val price:Float,
    val priceChange: Float,
    val percentageChange: Float,
    val exchangeShortName: String,
    val type: String
)