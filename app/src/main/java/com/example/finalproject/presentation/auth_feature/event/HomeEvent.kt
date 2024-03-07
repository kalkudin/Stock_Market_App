package com.example.finalproject.presentation.auth_feature.event

sealed class HomeEvent {
    data object LoginPressed : HomeEvent()
    data object RegisterPressed : HomeEvent()

    data object CheckCurrentSession : HomeEvent()
}