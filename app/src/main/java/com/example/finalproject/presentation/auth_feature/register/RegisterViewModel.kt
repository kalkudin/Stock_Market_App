package com.example.finalproject.presentation.auth_feature.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.AuthUseCases
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
import kotlinx.coroutines.flow.collect
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

    private fun registerUser(email: String, password: String, repeatPassword: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            authUseCases.registerUserUseCase(email, password, repeatPassword, firstName, lastName).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _registerFlow.update {
                        it.copy(isLoading = true) }

                    is Resource.Success -> {
                        _registerFlow.update {
                            it.copy(isSuccess = true, isLoading = false)
                        }
                        saveUserInitials(uid = resource.data, firstName = firstName, lastName = lastName)
                        navigateToLogin()
                    }

                    is Resource.Error -> {
                        val errorMessage = getErrorMessage(resource.errorType)
                        _registerFlow.update {
                            it.copy(
                                errorMessage = errorMessage,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun saveUserInitials(uid: String, firstName : String, lastName : String) {
        val job = viewModelScope.launch {
            authUseCases.saveUserInitialsUseCase(uid = uid, firstName = "jemal", lastName = "jiblibegashvili").collect { resource ->
                Log.d("RegisterVM", resource.toString())
            }
        }
        job.join()
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