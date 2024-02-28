package com.example.finalproject.data.remote.model.stock_news

data class StockNewsDto(
    val title: String,
    val date: String,
    val content: String,
    val tickers: String,
    val image: String,
    val link: String,
    val author: String,
    val site: String
)