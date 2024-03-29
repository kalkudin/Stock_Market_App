package com.example.finalproject.presentation.bottom_sheets.existing_cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.CreditCardUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.presentation.bottom_sheets.event.GetUserCardsEvent
import com.example.finalproject.presentation.bottom_sheets.state.UserCardsState
import com.example.finalproject.presentation.mapper.credit_card.toPresentation
import com.example.finalproject.presentation.mapper.base.handleStateUpdate
import com.example.finalproject.presentation.model.credit_card.CreditCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCreditCardsViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases,
    private val creditCardUseCases: CreditCardUseCases
) : ViewModel() {

    private val _creditCardsFlow = MutableStateFlow(UserCardsState())
    val creditCardFlow : StateFlow<UserCardsState> = _creditCardsFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<CreditCardsNavigationFlow>()
    val navigationFlow : SharedFlow<CreditCardsNavigationFlow> = _navigationFlow.asSharedFlow()

    fun onEvent(event: GetUserCardsEvent) {
        viewModelScope.launch {
            when (event) {
                is GetUserCardsEvent.GetUserCreditCards -> { getUserCreditCards() }
                is GetUserCardsEvent.NavigateBack -> navigateBack(card = event.card)
                is GetUserCardsEvent.UpdateDots -> updateDots(position = event.position)
            }
        }
    }

    private suspend fun getUserCreditCards() {
        val uid = dataStoreUseCases.readUserUidUseCase().first()

        _creditCardsFlow.update { state -> state.copy(isLoading = true) }

        creditCardUseCases.getUserCreditCardsUseCase(uid = uid).collect { resource ->
            handleStateUpdate(
                resource = resource,
                stateFlow = _creditCardsFlow,
                onSuccess = {list -> this.copy(cardList = list.map { it.toPresentation() })},
                onError = {errorMessage -> this.copy(errorMessage = errorMessage)}
            )
        }
        _creditCardsFlow.update { state -> state.copy(isLoading = false) }

    }

    private fun updateDots(position : Int) {

    }

    private suspend fun navigateBack(card : CreditCard) {
        _navigationFlow.emit(CreditCardsNavigationFlow.NavigateBack(creditCard = card))
    }
}

sealed class CreditCardsNavigationFlow {
    data class NavigateBack(val creditCard : CreditCard) : CreditCardsNavigationFlow()

}