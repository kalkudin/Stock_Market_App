package com.example.finalproject.data.remote.mapper.stock_news

import com.example.finalproject.data.remote.model.stock_news.NewsDto
import com.example.finalproject.data.remote.model.stock_news.StockNewsDto
import com.example.finalproject.domain.model.stock_news.GetNews
import com.example.finalproject.domain.model.stock_news.GetStockNews

fun StockNewsDto.toDomain(): GetStockNews {
    return GetStockNews(
        content = content.map { it.toDomain() }
    )
}

fun NewsDto.toDomain(): GetNews {
    return GetNews(
        title = title,
        date = date,
        content = content,
        image = image,
        link = link,
        author = author,
        site = site
    )
}