package com.example.finalproject.presentation.mapper.home

import com.example.finalproject.data.common.Resource
import com.example.finalproject.presentation.state.home.StockListState
import com.example.finalproject.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <T, S : Any> handleResourceUpdateHomePage(
    resource: Resource<T>,
    stateFlow: MutableStateFlow<S>,
    onSuccess: S.(T) -> S,
    onError: S.(String) -> S
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
                onError(currentState, getErrorMessage(resource.errorType))
            }
        }
        else -> {}
    }
}