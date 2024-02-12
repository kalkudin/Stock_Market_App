package com.example.finalproject.presentation.auth_feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.auth_usecase.LoginUserUseCase
import com.example.finalproject.domain.usecase.datastore_usecase.SaveUserSessionUseCase
import com.example.finalproject.domain.usecase.datastore_usecase.SaveUserUidUseCase
import com.example.finalproject.presentation.auth_feature.event.LoginEvent
import com.example.finalproject.presentation.auth_feature.state.LoginState
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
    private val loginUserUseCase: LoginUserUseCase,
    private val saveUserUidUseCase: SaveUserUidUseCase,
    private val saveUserSessionUseCase: SaveUserSessionUseCase
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
            loginUserUseCase(email, password).collect {
                when (it) {
                    is Resource.Success -> {
                        _loginFlow.update { currentState -> currentState.copy() }
                        saveUserUidUseCase(it.data)
                        saveUserSessionUseCase(rememberMe = saveSession)
                        loginSuccess(it.data)
                    }
                    is Resource.Error -> {
                        val errorMessage = getErrorMessage(it.errorType)
                        _loginFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
                    }
                    is Resource.Loading -> {
                        _loginFlow.update { currentState ->
                            currentState.copy(
                                isLoading = it.loading
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