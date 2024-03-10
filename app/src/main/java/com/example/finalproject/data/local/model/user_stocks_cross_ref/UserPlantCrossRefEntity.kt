package com.example.finalproject.data.local.model.user_stocks_cross_ref

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "stocksSymbol"])
data class UserStocksCrossRefEntity(
    val userId: String,
    val stocksSymbol: String
)
