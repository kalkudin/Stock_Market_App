package com.example.finalproject.presentation.bottom_sheets.new_card

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.R
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.CreditCardUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.presentation.bottom_sheets.event.AddNewCardEvent
import com.example.finalproject.presentation.bottom_sheets.state.NewCardState
import com.example.finalproject.presentation.model.bottom_sheets.NewCardType
import com.example.finalproject.presentation.model.credit_card.CreditCard
import com.example.finalproject.presentation.util.getErrorMessage
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
class AddNewCardViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases,
    private val creditCardUseCases: CreditCardUseCases,
) : ViewModel(){

    private val _successFlow = MutableStateFlow(NewCardState())
    val successFlow : StateFlow<NewCardState> = _successFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<NewCardNavigationFlow>()
    val navigationFlow : SharedFlow<NewCardNavigationFlow> = _navigationFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            _successFlow.update { state -> state.copy(cardTypeList = getCardList()) }
        }
    }

    fun onEvent(event : AddNewCardEvent) {
        viewModelScope.launch {
            when(event) {
                is AddNewCardEvent.AddCreditCard -> addCreditCard(expirationDate = event.expirationDate, cardNumber = event.cardNumber, ccv = event.ccv, cardType = event.cardType)
                is AddNewCardEvent.ResetErrorMessage -> resetError()
                is AddNewCardEvent.NavigateToFundsFragment -> navigateToFundsPage(expirationDate = event.expirationDate, cardNumber = event.cardNumber, ccv = event.ccv, cardType = event.cardType)
            }
        }
    }

    private suspend fun addCreditCard(expirationDate: String, cardNumber: List<String>, ccv: String, cardType : String) {
        val uid = dataStoreUseCases.readUserUidUseCase().first()
        Log.d("NewCardVM", cardType)
        creditCardUseCases.addCreditCardUseCase(uid = uid, cardNumber = cardNumber, expirationDate = expirationDate, ccv = ccv, cardType = cardType).collect { resource ->
            Log.d("NewCardVM", resource.toString())
            when(resource) {
                is Resource.Error -> {
                    _successFlow.update { state -> state.copy(errorMessage = getErrorMessage(resource.errorType), isLoading = false)}
                }
                is Resource.Loading -> {
                    _successFlow.update { state -> state.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    _successFlow.update { state -> state.copy(success = true, isLoading = false) }
                }

                else -> {}
            }
        }
    }

    private suspend fun navigateToFundsPage(expirationDate: String, cardNumber: List<String>, ccv: String, cardType : CreditCard.CardType) {
        _navigationFlow.emit(NewCardNavigationFlow.NavigateBack(
            creditCard = CreditCard(
                cardNumber = cardNumber.joinToString(separator = ""),
                expirationDate = expirationDate,
                ccv = ccv,
                cardType = cardType))
        )
    }

    private fun resetError() {
        _successFlow.update { state -> state.copy(errorMessage = null) }
    }

    private fun getCardList(): List<NewCardType> {
        return listOf(
            NewCardType(
                path = R.drawable.ic_blank_card_icon,
                isSelected = false,
                type = CreditCard.CardType.UNKNOWN),
            NewCardType(
                path = R.drawable.ic_master_card_icon,
                isSelected = false,
                type = CreditCard.CardType.MASTER_CARD),
            NewCardType(
                path = R.drawable.ic_visa_icon,
                isSelected = false,
                type = CreditCard.CardType.VISA)
        )
    }
}

sealed class NewCardNavigationFlow {
    data class NavigateBack(val creditCard : CreditCard) : NewCardNavigationFlow()
}