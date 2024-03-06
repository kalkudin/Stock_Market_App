package com.example.finalproject.presentation.profile_feature.mapper

import android.util.Log
import com.example.finalproject.data.common.Resource
import com.example.finalproject.presentation.profile_feature.state.ProfileState
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
