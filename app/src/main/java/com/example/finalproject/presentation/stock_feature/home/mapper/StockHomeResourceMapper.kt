package com.example.finalproject.presentation.stock_feature.home.mapper

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.stocks_to_watch.StockListItem
import com.example.finalproject.presentation.stock_feature.home.model.Stock
import com.example.finalproject.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.Flow

suspend fun handleResponse(
    resourceFlow: Flow<Resource<List<StockListItem>>>,
    performType: Stock.PerformingType,
    targetList: MutableList<Stock>,
    errorMessages: MutableList<String>
) {
    resourceFlow.collect { result ->
        when (result) {
            is Resource.Success -> {
                val stocks = result.data.map { it.toPresentation(performType) }.take(5)
                targetList.addAll(stocks)
            }
            is Resource.Error -> {
                val errorMessage = getErrorMessage(result.errorType)
                if (errorMessages.isEmpty()) {
                    errorMessages.add(errorMessage)
                }
            }
            else -> {}
        }
    }
}