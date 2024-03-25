package com.example.finalproject.data.local.model.stocks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StocksEntity(
    @PrimaryKey
    val symbol: String,
    @ColumnInfo(name = "price")
    val price: Float?,
    @ColumnInfo(name = "changes")
    val changes: Float?,
    @ColumnInfo(name = "companyName")
    val companyName: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image")
    val image: String
)
