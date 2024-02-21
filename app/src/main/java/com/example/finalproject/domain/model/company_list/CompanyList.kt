package com.example.finalproject.domain.model.company_list

data class CompanyList(
    val symbol: String,
    val name: String,
    val price: Float,
    val exchangeShortName: String,
    val type: String
)