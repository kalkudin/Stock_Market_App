package com.example.finalproject.presentation.auth_feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.auth_usecase.RegisterUserUseCase
import com.example.finalproject.presentation.auth_feature.event.RegisterEvent
import com.example.finalproject.presentation.auth_feature.state.RegisterState
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUserUseCase: RegisterUserUseCase) :
    ViewModel() {

    private val _registerFlow = MutableStateFlow(RegisterState())
    val registerFlow: StateFlow<RegisterState> = _registerFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<RegisterNavigationEvent>()
    val navigationFlow: SharedFlow<RegisterNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.BackPressed -> navigateToHome()
            is RegisterEvent.UserAlreadyExistsPressed -> navigateToLogin()
            is RegisterEvent.ResetFlow -> resetStateFlow()
            is RegisterEvent.Register -> registerUser(
                email = event.email,
                password = event.password,
                repeatPassword = event.repeatPassword
            )
        }
    }

    private fun registerUser(email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
//            _registerFlow.value = RegisterState(isLoading = true)
            registerUserUseCase(email, password, repeatPassword).collect {
                when (it) {
                    is Resource.Loading -> _registerFlow.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Success -> {
                        _registerFlow.update { currentState -> currentState.copy() }
                        navigateToLogin()
                    }

                    is Resource.Error -> {
                        val errorMessage = getErrorMessage(it.errorType)
                        _registerFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        viewModelScope.launch {
            _navigationFlow.emit(RegisterNavigationEvent.NavigateToHome)
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            _navigationFlow.emit(RegisterNavigationEvent.NavigateToLogin)
        }
    }

    private fun resetStateFlow() {
        _registerFlow.value = RegisterState()
    }
}

sealed class RegisterNavigationEvent {
    data object NavigateToHome : RegisterNavigationEvent()
    data object NavigateToLogin : RegisterNavigationEvent()
}