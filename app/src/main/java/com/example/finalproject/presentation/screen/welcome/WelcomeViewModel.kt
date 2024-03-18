package com.example.finalproject.presentation.screen.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.presentation.event.welcome.WelcomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<HomeNavigationEvent>()
    val navigationFlow : SharedFlow<HomeNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event : WelcomeEvent) {
        when(event) {
            is WelcomeEvent.LoginPressed -> navigateUserToLogin()
            is WelcomeEvent.RegisterPressed -> navigateUserToRegister()
            is WelcomeEvent.CheckCurrentSession -> checkCurrentSession()
        }
    }

    private fun navigateUserToLogin() {
        viewModelScope.launch {
            _navigationFlow.emit(HomeNavigationEvent.NavigateToLogin)
        }
    }

    private fun navigateUserToRegister() {
        viewModelScope.launch {
            _navigationFlow.emit(HomeNavigationEvent.NavigateToRegister)
        }
    }

    private fun checkCurrentSession() {
        viewModelScope.launch {
            if(dataStoreUseCases.readUserSessionUseCase().first()) {_navigationFlow.emit(HomeNavigationEvent.NavigateToStockHome)}
        }
    }
}

sealed class HomeNavigationEvent {
    data object NavigateToLogin : HomeNavigationEvent()
    data object NavigateToRegister : HomeNavigationEvent()
    data object NavigateToStockHome : HomeNavigationEvent()
}