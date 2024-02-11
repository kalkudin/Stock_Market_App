package com.example.finalproject.data.common

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorType: ErrorType) : Resource<Nothing>()
}