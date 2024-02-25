package com.example.finalproject.presentation.stock_feature.home.mapper

import com.example.finalproject.domain.model.stocks_to_watch.StockListItem
import com.example.finalproject.presentation.stock_feature.home.model.Stock
import com.example.finalproject.presentation.stock_feature.home.model.Stock.PerformingType.*

fun StockListItem.toPresentation(performingType : Stock.PerformingType) : Stock {
    return Stock(
        symbol = symbol,
        name = name,
        price = "$price $",
        change = "($change $)",
        changeValue = change,
        changesPercentage = "$changesPercentage %",
        performingType = performingType
    )
}