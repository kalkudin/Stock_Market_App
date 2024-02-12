package com.example.finalproject.presentation.auth_feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.datastore_usecase.ClearUserSessionUseCase
import com.example.finalproject.domain.usecase.datastore_usecase.ReadUserSessionUseCase
import com.example.finalproject.domain.usecase.datastore_usecase.ReadUserUidUseCase
import com.example.finalproject.presentation.auth_feature.event.HomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val readUserSessionUseCase: ReadUserSessionUseCase) : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<HomeNavigationEvent>()
    val navigationFlow : SharedFlow<HomeNavigationEvent> = _navigationFlow.asSharedFlow()

    init {
        checkCurrentSession()
    }

    fun onEvent(event : HomeEvent) {
        when(event) {
            is HomeEvent.LoginPressed -> navigateUserToLogin()
            is HomeEvent.RegisterPressed -> navigateUserToRegister()
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
            readUserSessionUseCase().collect {
                when(it){
                    true -> _navigationFlow.emit(HomeNavigationEvent.NavigateToStockHome)
                    else -> {}
                }
            }
        }
    }
}

sealed class HomeNavigationEvent {
    data object NavigateToLogin : HomeNavigationEvent()
    data object NavigateToRegister : HomeNavigationEvent()
    data object NavigateToStockHome : HomeNavigationEvent()
}