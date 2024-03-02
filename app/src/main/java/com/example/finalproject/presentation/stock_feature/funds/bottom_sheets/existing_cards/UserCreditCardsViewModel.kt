package com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.existing_cards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.CreditCardUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.event.GetUserCardsEvent
import com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.model.UserCardsState
import com.example.finalproject.presentation.stock_feature.funds.mapper.toPresentation
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    fun onEvent(event: GetUserCardsEvent) {
        viewModelScope.launch {
            when (event) {
                is GetUserCardsEvent.GetUserCreditCards -> { getUserCreditCards() }
            }
        }
    }

    private suspend fun getUserCreditCards() {
        val uid = dataStoreUseCases.readUserUidUseCase().first()

        creditCardUseCases.getUserCreditCardsUseCase(uid = uid).collect() { resource ->
            Log.d("UserCreditsVM", resource.toString())
            when(resource) {
                is Resource.Error -> { _creditCardsFlow.update { state ->
                    state.copy(isLoading = false, errorMessage = getErrorMessage(resource.errorType)) }
                }
                is Resource.Loading -> { _creditCardsFlow.update { state ->
                    state.copy(isLoading = true) }
                }
                is Resource.Success -> { _creditCardsFlow.update { state ->
                    state.copy(isLoading = false, cardList = resource.data.map { it.toPresentation() })}
                }
            }
        }
    }
}