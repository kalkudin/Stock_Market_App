package com.example.finalproject.presentation.event.stock_news

import com.example.finalproject.presentation.model.stock_news.News

sealed class StockNewsEvent {
    data object GetNewsList : StockNewsEvent()
    data class NewsItemClick (val item :News): StockNewsEvent()
}