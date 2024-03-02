package com.example.finalproject.presentation.stock_feature.stock_news.model

data class StockNewsModel(
    val title: String,
    val date: String,
    val content: String,
    val tickers: String,
    val image: String,
    val link: String,
    val author: String,
    val site: String
)