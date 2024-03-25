package com.example.finalproject.presentation.screen.login

import android.util.Log
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
        viewModelScope.launch {
            when (event) {
                is LoginEvent.ResetFlow -> resetStateFlow()
                is LoginEvent.LoginSuccess -> loginSuccess()
                is LoginEvent.BackPressed -> navigateToHome()
                is LoginEvent.UserNotAuthenticatedPressed -> navigateToRegister()
                is LoginEvent.ForgotPasswordPressed -> navigateToForgotPassword()
                is LoginEvent.Login -> loginUser(email = event.email, password = event.password, saveSession = false)
                is LoginEvent.LoginAndRememberUser -> loginUser(email = event.email, password = event.password, saveSession = true)
            }
        }
    }

    private suspend fun loginUser(email: String, password: String, saveSession: Boolean) {

        authUseCases.loginUserUseCase(email, password).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    saveUserUid(uid = resource.data)
                    saveUserSession(saveSession = saveSession)
                    _loginFlow.update { state ->
                        state.copy(isLoading = false, isSuccess = resource.data)
                    }
                }
                is Resource.Error -> {
                    _loginFlow.update { errorState ->
                        errorState.copy(isLoading = false, errorMessage = getErrorMessage(resource.errorType))
                    }
                }
                is Resource.Loading -> {
                    _loginFlow.update { loadingState ->
                        loadingState.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private suspend fun loginSuccess() {
        _navigationFlow.emit(LoginNavigationEvent.NavigateToStockHome)

    }

    private suspend fun navigateToHome() {
        _navigationFlow.emit(LoginNavigationEvent.NavigateToHome)

    }

    private suspend fun navigateToRegister() {
        _navigationFlow.emit(LoginNavigationEvent.NavigateToRegister)

    }

    private suspend  fun navigateToForgotPassword() {
        _navigationFlow.emit(LoginNavigationEvent.NavigateToForgotPassword)

    }

    private suspend fun saveUserUid(uid : String) {
        dataStoreUseCases.saveUserUidUseCase(userUid = uid)
    }

    private suspend fun saveUserSession(saveSession : Boolean) {
        Log.d("SessionVM", "SaveSessionCalled with $saveSession")
        dataStoreUseCases.saveUserSessionUseCase(rememberMe = saveSession)
    }

    private fun resetStateFlow() {
        _loginFlow.update { state -> state.copy(isLoading = false, errorMessage = null, isSuccess = null) }
    }
}

sealed class LoginNavigationEvent {
    data object NavigateToForgotPassword : LoginNavigationEvent()
    data object NavigateToRegister : LoginNavigationEvent()
    data object NavigateToHome : LoginNavigationEvent()
    data object NavigateToStockHome : LoginNavigationEvent()
}