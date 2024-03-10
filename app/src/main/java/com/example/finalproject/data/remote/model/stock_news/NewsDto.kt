package com.example.finalproject.data.remote.model.stock_news

data class NewsDto(
    val title: String,
    val date: String,
    val content: String,
    val image: String,
    val link: String,
    val author: String,
    val site: String
)
