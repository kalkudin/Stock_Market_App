package com.example.finalproject.presentation.stock_feature.credit_card

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.AddCreditCardUseCase
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.presentation.stock_feature.credit_card.event.AddCreditCardEvent
import com.example.finalproject.presentation.stock_feature.funds.FundsNavigationEvent
import com.example.finalproject.presentation.stock_feature.funds.event.UserFundsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCreditCardViewModel @Inject constructor(private val addCreditCardUseCase: AddCreditCardUseCase,
    private val dataStoreUseCases: DataStoreUseCases) : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<AddCreditCardNavigationEvent>()
    val navigationFlow : SharedFlow<AddCreditCardNavigationEvent> = _navigationFlow.asSharedFlow()

    init {
        viewModelScope.launch {

        }
    }

    fun onEvent(event: AddCreditCardEvent) {
        viewModelScope.launch {
            when(event) {
                is AddCreditCardEvent.BackButtonPressed -> navigateBack()
                is AddCreditCardEvent.AddCreditCard -> addCreditCard(event.expirationDate, event.cardNumber, event.ccv)
            }
        }
    }

    private suspend fun navigateBack() {
        _navigationFlow.emit(AddCreditCardNavigationEvent.NavigateBack)
    }

    private suspend fun addCreditCard(expirationDate : String, cardNumber : List<String>, ccv : String) {
        val uid = dataStoreUseCases.readUserUidUseCase().first()
        addCreditCardUseCase(uid = uid, cardNumber, expirationDate, ccv).collect {
            Log.d("Credit", it.toString())
        }
    }
}

sealed class AddCreditCardNavigationEvent() {
    data object NavigateBack : AddCreditCardNavigationEvent()
}