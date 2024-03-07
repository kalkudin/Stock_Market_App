package com.example.finalproject.presentation.mapper.profile

import com.example.finalproject.data.common.Resource
import com.example.finalproject.presentation.state.profile.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <T> handleResourceUpdate(
    resource: Resource<T>,
    stateFlow: MutableStateFlow<ProfileState>,
    onSuccess: ProfileState.(T) -> ProfileState,
    onError: ProfileState.(String) -> ProfileState
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
