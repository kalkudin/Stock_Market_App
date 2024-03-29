package com.example.finalproject.presentation.screen.funds

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.AuthUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.TransactionsUseCases
import com.example.finalproject.domain.usecase.UserFundsUseCases
import com.example.finalproject.presentation.event.funds.UserFundsEvent
import com.example.finalproject.presentation.mapper.home.formatFirstName
import com.example.finalproject.presentation.mapper.profile.toPresentation
import com.example.finalproject.presentation.state.funds.FundsState
import com.example.finalproject.presentation.util.formatName
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
    private val transactionsUseCases: TransactionsUseCases,
    private val authUseCases: AuthUseCases
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
                is UserFundsEvent.ResetFlow -> resetFlow()
                is UserFundsEvent.OpenCardsBottomSheet -> openCardsBottomSheet()
                is UserFundsEvent.OpenNewCardBottomSheet -> openNewCardBottomSheet()
                is UserFundsEvent.GetUserName -> getUserName()
            }
        }
    }

    private suspend fun navigateBack() {
        _navigationFlow.emit(FundsNavigationEvent.NavigateBack)
    }

    private suspend fun openCardsBottomSheet() {
        _navigationFlow.emit(FundsNavigationEvent.OpenCardsBottomSheet)
    }

    private suspend fun openNewCardBottomSheet() {
        _navigationFlow.emit(FundsNavigationEvent.OpenNewCardBottomSheet)
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

                    transactionsUseCases.saveTransactionUseCase(
                        uid = uid,
                        amount = amount.toDouble(),
                        description = "Added $amount $ to account",
                        type = "outgoing"
                    ).collect()
                }
            }
        }
    }

    private suspend fun getUserName() {
        val uid = dataStoreUseCases.readUserUidUseCase().first()

        authUseCases.getUserInitialsUseCase(uid = uid).collect { resource ->
            when(resource) {
                is Resource.Success -> {
                    _successFlow.update { state -> state.copy(userInitials = resource.data.formatName())}
                }
                else -> {}
            }
        }
    }

    private fun resetFlow() {
        _successFlow.update { state -> state.copy(isLoading = false, errorMessage = null, success = false) }
    }
}

sealed class FundsNavigationEvent {
    data object NavigateBack : FundsNavigationEvent()
    data object OpenCardsBottomSheet : FundsNavigationEvent()
    data object OpenNewCardBottomSheet : FundsNavigationEvent()
}