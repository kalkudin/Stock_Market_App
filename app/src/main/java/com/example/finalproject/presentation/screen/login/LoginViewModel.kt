package com.example.finalproject.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.AuthUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.presentation.event.login.LoginEvent
import com.example.finalproject.presentation.state.login.LoginState
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
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val dataStoreUseCases: DataStoreUseCases,
) : ViewModel() {

    private val _loginFlow = MutableStateFlow(LoginState())
    val loginFlow : StateFlow<LoginState> = _loginFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<LoginNavigationEvent>()
    val navigationFlow: SharedFlow<LoginNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.BackPressed -> navigateToHome()
            is LoginEvent.UserNotAuthenticatedPressed -> navigateToRegister()
            is LoginEvent.ForgotPasswordPressed -> navigateToForgotPassword()
            is LoginEvent.ResetFlow -> resetStateFlow()
            is LoginEvent.Login -> loginUser(email = event.email, password = event.password, saveSession = false)
            is LoginEvent.LoginAndRememberUser -> loginUser(email = event.email, password = event.password, saveSession = true)
        }
    }

    private fun loginUser(email: String, password: String, saveSession: Boolean) {
        viewModelScope.launch {
            authUseCases.loginUserUseCase(email, password).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _loginFlow.update { it.copy(isLoading = false, isSuccess = resource.data) }
                        dataStoreUseCases.saveUserUidUseCase(resource.data)
                        dataStoreUseCases.saveUserSessionUseCase(rememberMe = saveSession)
                        loginSuccess(resource.data)
                    }
                    is Resource.Error -> {
                        val errorMessage = getErrorMessage(resource.errorType)
                        _loginFlow.update { it.copy(isLoading = false, errorMessage = errorMessage) }
                    }
                    is Resource.Loading -> {
                        _loginFlow.update { currentState ->
                            currentState.copy(
                                isLoading = resource.loading
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loginSuccess(id : String) {
        viewModelScope.launch {
            _navigationFlow.emit(LoginNavigationEvent.NavigateToStockHome)
        }
    }

    private fun navigateToHome() {
        viewModelScope.launch {
            _navigationFlow.emit(LoginNavigationEvent.NavigateToHome)
        }
    }

    private fun navigateToRegister() {
        viewModelScope.launch {
            _navigationFlow.emit(LoginNavigationEvent.NavigateToRegister)
        }
    }

    private fun navigateToForgotPassword() {
        viewModelScope.launch {
            _navigationFlow.emit(LoginNavigationEvent.NavigateToForgotPassword)
        }
    }

    private fun resetStateFlow() {
        _loginFlow.value = LoginState()
    }
}

sealed class LoginNavigationEvent {
    data object NavigateToForgotPassword : LoginNavigationEvent()
    data object NavigateToRegister : LoginNavigationEvent()
    data object NavigateToHome : LoginNavigationEvent()
    data object NavigateToStockHome : LoginNavigationEvent()
}