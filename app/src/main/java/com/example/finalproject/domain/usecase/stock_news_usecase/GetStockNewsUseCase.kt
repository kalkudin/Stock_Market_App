package com.example.finalproject.domain.usecase.stock_news_usecase

import com.example.finalproject.domain.repository.stock_news.StockNewsRepository
import javax.inject.Inject

class GetStockNewsUseCase @Inject constructor(
    private val stockNewsRepository: StockNewsRepository
){
    suspend operator fun invoke(page: Int) = stockNewsRepository.getStockNews(page)
}