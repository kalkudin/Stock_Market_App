package com.example.finalproject.presentation.mapper.stock_news

import com.example.finalproject.domain.model.stock_news.GetNews
import com.example.finalproject.domain.model.stock_news.GetStockNews
import com.example.finalproject.presentation.model.stock_news.News
import com.example.finalproject.presentation.model.stock_news.StockNews
import org.jsoup.Jsoup

fun GetStockNews.toPresentation(): StockNews {
    return StockNews(
        content = content.map { it.toPresentation() }
    )
}

fun GetNews.toPresentation(): News {
    val document = Jsoup.parse(this.content)
    val paragraphs = document.select("p")
    var parsedContent = ""
    paragraphs.forEach { paragraph ->
        parsedContent += paragraph.text() + "\n"
    }
    return News(
        title = title,
        date = date,
        content = parsedContent,
        image = image,
        link = link,
        author = author,
        site = site
    )
}