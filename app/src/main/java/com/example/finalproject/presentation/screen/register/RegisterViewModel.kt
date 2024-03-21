package com.example.finalproject.presentation.screen.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.AuthUseCases
import com.example.finalproject.presentation.event.register.RegisterEvent
import com.example.finalproject.presentation.state.register.RegisterState
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
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) :
    ViewModel() {

    private val _registerFlow = MutableStateFlow(RegisterState())
    val registerFlow: StateFlow<RegisterState> = _registerFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<RegisterNavigationEvent>()
    val navigationFlow: SharedFlow<RegisterNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        viewModelScope.launch {
            when (event) {
                is RegisterEvent.BackPressed -> navigateToHome()
                is RegisterEvent.UserAlreadyExistsPressed -> navigateToLogin()
                is RegisterEvent.ResetFlow -> resetStateFlow()
                is RegisterEvent.Register -> registerUser(
                    email = event.email,
                    password = event.password,
                    repeatPassword = event.repeatPassword,
                    firstName = event.firstName,
                    lastName =  event.lastName,
                )
            }
        }
    }

    private suspend fun registerUser(email: String, password: String, repeatPassword: String, firstName: String, lastName: String) {
        authUseCases.registerUserUseCase(email, password, repeatPassword, firstName, lastName).collect { resource ->
            when (resource) {
                is Resource.Loading -> _registerFlow.update { state ->
                    state.copy(isLoading = true)
                }
                is Resource.Success -> {
                    saveUserInitials(uid = resource.data, firstName = firstName, lastName = lastName)

                    _registerFlow.update { state ->
                        state.copy(isSuccess = true, isLoading = false)
                    }
                    navigateToLogin()
                }
                is Resource.Error -> {
                    _registerFlow.update {
                        it.copy(errorMessage = getErrorMessage(resource.errorType), isLoading = false)
                    }
                }
            }
        }
    }

    private suspend fun saveUserInitials(uid: String, firstName : String, lastName : String) {
        val userInitialsJob = viewModelScope.launch {
            authUseCases.saveUserInitialsUseCase(uid = uid, firstName = firstName, lastName = lastName).collect {  }
        }
        userInitialsJob.join()
    }

    private suspend fun navigateToHome() {
        _navigationFlow.emit(RegisterNavigationEvent.NavigateToHome)
    }

    private suspend fun navigateToLogin() {
        _navigationFlow.emit(RegisterNavigationEvent.NavigateToLogin)

    }

    private fun resetStateFlow() {
        _registerFlow.update { state -> state.copy(isLoading = false, errorMessage = null, isSuccess = false) }
    }
}

sealed class RegisterNavigationEvent {
    data object NavigateToHome : RegisterNavigationEvent()
    data object NavigateToLogin : RegisterNavigationEvent()
}