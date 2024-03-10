package com.example.finalproject.presentation.mapper.stock_news

import com.example.finalproject.domain.model.stock_news.News
import com.example.finalproject.domain.model.stock_news.StockNews
import com.example.finalproject.presentation.model.stock_news.NewsModel
import com.example.finalproject.presentation.model.stock_news.StockNewsModel
import org.jsoup.Jsoup

fun StockNews.toPresentation(): StockNewsModel {
    return StockNewsModel(
        content = content.map { it.toPresentation() }
    )
}

fun News.toPresentation(): NewsModel {
    val document = Jsoup.parse(this.content)
    val paragraphs = document.select("p")
    var parsedContent = ""
    paragraphs.forEach { paragraph ->
        parsedContent += paragraph.text() + "\n"
    }
    return NewsModel(
        title = title,
        date = date,
        content = parsedContent,
        image = image,
        link = link,
        author = author,
        site = site
    )
}