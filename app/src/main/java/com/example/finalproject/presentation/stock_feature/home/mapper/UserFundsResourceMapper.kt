package com.example.finalproject.presentation.stock_feature.home.mapper

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.UserFunds
import com.example.finalproject.presentation.stock_feature.home.state.StockListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun handleUserFundsResult(
    result: Resource<UserFunds>,
    stockState: MutableStateFlow<StockListState>,
    formatUserFunds: (Double) -> String,
    getErrorMessage: (ErrorType) -> String
) {
    when (result) {
        is Resource.Success -> {
            stockState.update { currentState ->
                currentState.copy(
                    userFunds = formatUserFunds(result.data.amount)
                )
            }
        }
        is Resource.Error -> {
            val errorMessage = getErrorMessage(result.errorType)
            stockState.update { currentState ->
                currentState.copy(
                    errorMessage = errorMessage
                )
            }
        }
        is Resource.Loading -> {

        }
    }
}