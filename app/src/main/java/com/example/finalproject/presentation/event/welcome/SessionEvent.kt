package com.example.finalproject.presentation.event.welcome

sealed class SessionEvent{
    data object CheckCurrentSession : SessionEvent()
}