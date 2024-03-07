package com.example.finalproject.presentation.mapper.home

import com.example.finalproject.data.common.Resource
import com.example.finalproject.presentation.state.home.StockListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <T> handleResourceUpdateHomePage(
    resource: Resource<T>,
    stateFlow: MutableStateFlow<StockListState>,
    onSuccess: StockListState.(T) -> StockListState,
    onError: StockListState.(String) -> StockListState
){
    when (resource) {
        is Resource.Success -> {
            resource.data?.let { data ->
                stateFlow.update { currentState ->
                    onSuccess(currentState, data)
                }
            }
        }
        is Resource.Error -> {
            stateFlow.update { currentState ->
                onError(currentState, resource.errorType.toString())
            }
        }
        else -> {}
    }
}