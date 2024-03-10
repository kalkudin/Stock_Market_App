package com.example.finalproject.presentation.event.stock_news

import com.example.finalproject.presentation.model.stock_news.NewsModel

sealed class StockNewsEvent {
    data object GetNewsList : StockNewsEvent()
    data class NewsItemClick (val item :NewsModel): StockNewsEvent()
}