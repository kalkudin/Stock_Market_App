package com.example.finalproject.data.local.mapper.user_stocks_cross_ref

import com.example.finalproject.data.local.model.user_stocks_cross_ref.UserStocksCrossRefEntity
import com.example.finalproject.domain.model.user_stocks_cross_ref.UserStocksCrossRef

fun UserStocksCrossRefEntity.toDomain(): UserStocksCrossRef {
    return UserStocksCrossRef(
        userId = userId,
        stocksSymbol = stocksSymbol
    )
}

fun UserStocksCrossRef.toData(): UserStocksCrossRefEntity {
    return UserStocksCrossRefEntity(
        userId = userId,
        stocksSymbol = stocksSymbol
    )
}