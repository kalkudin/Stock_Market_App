package com.example.finalproject.presentation.auth_feature.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.auth_usecase.ForgotPasswordUseCase
import com.example.finalproject.presentation.auth_feature.event.ForgotPasswordEvent
import com.example.finalproject.presentation.auth_feature.state.ForgotPasswordState
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val forgotPasswordUseCase: ForgotPasswordUseCase): ViewModel() {

    private val _forgotPasswordFlow = MutableStateFlow(ForgotPasswordState())
    val forgotPasswordFlow : StateFlow<ForgotPasswordState> = _forgotPasswordFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<ForgotPasswordNavigationEvent>()
    val navigationFlow : SharedFlow<ForgotPasswordNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event : ForgotPasswordEvent) {
        when(event) {
            is ForgotPasswordEvent.BackPressed -> goToHome()
            is ForgotPasswordEvent.BackToLoginPressed -> goToLogin()
            is ForgotPasswordEvent.ResetPassword -> sendResetPasswordEmail(email = event.email)
        }
    }

    private fun sendResetPasswordEmail(email: String) {
        viewModelScope.launch {
            _forgotPasswordFlow.value = ForgotPasswordState(isLoading = true)
            forgotPasswordUseCase(email).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _forgotPasswordFlow.value = ForgotPasswordState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _forgotPasswordFlow.value = ForgotPasswordState(isSuccess = true)
                    }
                    is Resource.Error -> {
                        _forgotPasswordFlow.value = ForgotPasswordState(
                            isLoading = false,
                            errorMessage = getErrorMessage(result.errorType)
                        )
                    }
                }
            }
        }
    }

    private fun goToLogin() {
        viewModelScope.launch {
            _navigationFlow.emit(ForgotPasswordNavigationEvent.GoToLogin)
        }
    }

    private fun goToHome() {
        viewModelScope.launch {
            _navigationFlow.emit(ForgotPasswordNavigationEvent.GoToHome)
        }
    }
}

sealed class ForgotPasswordNavigationEvent {
    data object GoToLogin : ForgotPasswordNavigationEvent()
    data object GoToHome : ForgotPasswordNavigationEvent()
}