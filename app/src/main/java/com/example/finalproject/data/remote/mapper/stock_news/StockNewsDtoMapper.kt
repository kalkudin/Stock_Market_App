package com.example.finalproject.data.remote.mapper.stock_news

import com.example.finalproject.data.remote.model.stock_news.StockNewsDto
import com.example.finalproject.domain.model.stock_news.StockNews
import org.jsoup.Jsoup

fun StockNewsDto.toDomain(): StockNews {
    val parsedContent = Jsoup.parse(content).text()
    return StockNews(
        title = title,
        date = date,
        content = parsedContent,
        tickers = tickers,
        image = image,
        link = link,
        author = author,
        site = site
    )
}