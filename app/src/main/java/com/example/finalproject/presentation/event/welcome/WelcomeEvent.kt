package com.example.finalproject.presentation.event.welcome

sealed class WelcomeEvent {
    data object LoginPressed : WelcomeEvent()
    data object RegisterPressed : WelcomeEvent()

    data object CheckCurrentSession : WelcomeEvent()
}