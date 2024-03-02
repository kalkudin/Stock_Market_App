package com.example.finalproject.presentation.stock_feature.stock_news.mapper

import com.example.finalproject.domain.model.stock_news.StockNews
import com.example.finalproject.presentation.stock_feature.stock_news.model.StockNewsModel

fun StockNews.toPresentation(): StockNewsModel {
    return StockNewsModel(
        title = title,
        date = date,
        content = content,
        tickers = tickers,
        image = image,
        link = link,
        author = author,
        site = site
    )
}