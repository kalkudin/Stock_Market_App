package com.example.finalproject.data.remote.model.stocks_to_watch

data class StockListDto(
    val symbol : String,
    val name : String,
    val change : Double,
    val price : Double,
    val changesPercentage : Double
)