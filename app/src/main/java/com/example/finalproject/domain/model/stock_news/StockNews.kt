package com.example.finalproject.domain.model.stock_news

data class StockNews(
    val title: String,
    val date: String,
    val content: String,
    val tickers: String,
    val image: String,
    val link: String,
    val author: String,
    val site: String
)