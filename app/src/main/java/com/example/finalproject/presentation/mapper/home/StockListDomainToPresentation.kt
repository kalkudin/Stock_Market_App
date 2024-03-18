package com.example.finalproject.presentation.mapper.home

import com.example.finalproject.domain.model.stocks_to_watch.GetStockListItem
import com.example.finalproject.presentation.model.home.Stock

fun GetStockListItem.toPresentation(performingType : Stock.PerformingType) : Stock {
    return Stock(
        symbol = symbol,
        name = name,
        price = "$ $price ",
        change = if(change >= 0) "+$change" else "$change",
        changeValue = change,
        changesPercentage = "($changesPercentage %)",
        performingType = performingType
    )
}