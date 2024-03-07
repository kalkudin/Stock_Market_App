package com.example.finalproject.presentation.event.welcome

sealed class HomeEvent {
    data object LoginPressed : HomeEvent()
    data object RegisterPressed : HomeEvent()

    data object CheckCurrentSession : HomeEvent()
}