package com.example.finalproject.presentation.model.company_list

data class CompanyListModel (
    val symbol: String,
    val name: String,
    val price:Float,
    val exchangeShortName: String,
    val type: String
)