package com.example.finalproject.presentation.mapper.base

import com.example.finalproject.data.common.Resource
import com.example.finalproject.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <T, S : Any> handleStateUpdate(
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