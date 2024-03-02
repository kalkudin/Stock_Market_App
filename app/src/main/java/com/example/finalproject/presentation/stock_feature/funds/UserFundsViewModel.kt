package com.example.finalproject.presentation.stock_feature.funds

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.CreditCardUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.UserFundsUseCases
import com.example.finalproject.presentation.stock_feature.funds.event.UserFundsEvent
import com.example.finalproject.presentation.stock_feature.funds.state.FundsState
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserFundsViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases,
    private val fundsUseCases: UserFundsUseCases,
) : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<FundsNavigationEvent>()
    val navigationFlow : SharedFlow<FundsNavigationEvent> = _navigationFlow.asSharedFlow()

    private val _successFlow = MutableStateFlow(FundsState())
    val successFlow : StateFlow<FundsState> = _successFlow.asStateFlow()

    fun onEvent(event: UserFundsEvent) {
        viewModelScope.launch {
            when(event) {
                is UserFundsEvent.BackButtonPressed -> navigateBack()
                is UserFundsEvent.AddFunds -> addFundsToUsersAccount(amount = event.amount, cardNumber = event.cardNumber)
                UserFundsEvent.ResetFlow -> resetFlow()
            }
        }
    }

    private suspend fun navigateBack() {
        _navigationFlow.emit(FundsNavigationEvent.NavigateBack)
    }

    private suspend fun addFundsToUsersAccount(amount : String, cardNumber : String) {
        val uid = dataStoreUseCases.readUserUidUseCase().first()

        fundsUseCases.addUserFundsUseCase(uid = uid, amount = amount, cardNumber = cardNumber).collect { resource ->
            Log.d("FundsVM", resource.toString())
            when(resource) {
                is Resource.Error -> {
                    _successFlow.update { state -> state.copy(isLoading = false, errorMessage = getErrorMessage(resource.errorType)) }
                }
                is Resource.Loading -> {
                    _successFlow.update { state -> state.copy(isLoading = true)}
                }
                is Resource.Success -> {
                    _successFlow.update { state -> state.copy(isLoading = false, success = true)}
                }
            }
        }
    }

    private fun resetFlow() {
        _successFlow.update { state -> state.copy(isLoading = false, errorMessage = null, success = false) }
    }
}

sealed class FundsNavigationEvent {
    data object NavigateBack : FundsNavigationEvent()
}