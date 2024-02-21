package com.example.finalproject.data.remote.model.company_list

data class CompanyListDto(
    val symbol: String,
    val name: String,
    val price:Float,
    val exchangeShortName: String,
    val type: String
)