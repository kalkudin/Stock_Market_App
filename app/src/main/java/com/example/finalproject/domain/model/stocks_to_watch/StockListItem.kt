package com.example.finalproject.domain.model.stocks_to_watch

data class StockListItem(
    val symbol : String,
    val name : String,
    val change : Double,
    val price : Double,
    val changesPercentage : Double
)