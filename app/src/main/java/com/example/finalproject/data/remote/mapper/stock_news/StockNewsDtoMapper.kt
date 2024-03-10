package com.example.finalproject.data.remote.mapper.stock_news

import com.example.finalproject.data.remote.model.stock_news.NewsDto
import com.example.finalproject.data.remote.model.stock_news.StockNewsDto
import com.example.finalproject.domain.model.stock_news.News
import com.example.finalproject.domain.model.stock_news.StockNews

fun StockNewsDto.toDomain(): StockNews {
    return StockNews(
        content = content.map { it.toDomain() }
    )
}

fun NewsDto.toDomain(): News {
    return News(
        title = title,
        date = date,
        content = content,
        image = image,
        link = link,
        author = author,
        site = site
    )
}