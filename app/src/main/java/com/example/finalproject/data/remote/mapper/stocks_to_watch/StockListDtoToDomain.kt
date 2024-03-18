package com.example.finalproject.data.remote.mapper.stocks_to_watch

import com.example.finalproject.data.remote.model.stocks_to_watch.StockListDto
import com.example.finalproject.domain.model.stocks_to_watch.GetStockListItem

fun StockListDto.toDomain(): GetStockListItem {
    return GetStockListItem(
        symbol = symbol,
        name = name,
        change = change,
        price = price,
        changesPercentage = changesPercentage
    )
}