package com.example.finalproject.presentation.event.profile

sealed class FavoriteStocksEvent {
    data object NavigateBack : FavoriteStocksEvent()
    data object GetFavoriteStocks : FavoriteStocksEvent()
    data class RemoveSelectedStocks(val symbols : List<String>) : FavoriteStocksEvent()
    data class NavigateToDetails(val symbol: String) : FavoriteStocksEvent()
}