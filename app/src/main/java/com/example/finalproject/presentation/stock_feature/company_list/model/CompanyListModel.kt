package com.example.finalproject.presentation.stock_feature.company_list.model

data class CompanyListModel (
    val symbol: String,
    val name: String,
    val price:Float,
    val exchangeShortName: String,
    val type: String
)