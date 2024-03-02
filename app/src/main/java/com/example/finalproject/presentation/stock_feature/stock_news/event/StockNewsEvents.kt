package com.example.finalproject.presentation.stock_feature.stock_news.event

sealed class StockNewsEvents {
    data class NavigateToStockNewsDetail(val url: String): StockNewsEvents()
    data class GetStockNews(val page: Int): StockNewsEvents()
}