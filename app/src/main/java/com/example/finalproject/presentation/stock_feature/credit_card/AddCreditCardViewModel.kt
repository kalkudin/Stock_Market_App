package com.example.finalproject.presentation.stock_feature.credit_card

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.credit_card_usecase.AddCreditCardUseCase
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.credit_card_usecase.GetUserCreditCardsUseCase
import com.example.finalproject.domain.usecase.credit_card_usecase.RemoveUserCreditCardUseCase
import com.example.finalproject.presentation.stock_feature.credit_card.event.AddCreditCardEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCreditCardViewModel @Inject constructor(
    private val addCreditCardUseCase: AddCreditCardUseCase,
    private val getUserCreditCardsUseCase: GetUserCreditCardsUseCase,
    private val removeUserCreditCardUseCase: RemoveUserCreditCardUseCase,
    private val dataStoreUseCases: DataStoreUseCases,
) : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<AddCreditCardNavigationEvent>()
    val navigationFlow: SharedFlow<AddCreditCardNavigationEvent> = _navigationFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            val my_val = dataStoreUseCases.readUserUidUseCase().first()
//            addCreditCardUseCase(
//                uid = my_val,
//                cardNumber = "4344222233334344".chunked(4),
//                expirationDate = "12/34",
//                ccv = "432"
//            ).collect() {
//                Log.d("Credit", it.toString())
//            }
//            getUserCreditCardsUseCase(my_val).collect {
//                Log.d("Credit", it.toString())
//            }
            removeUserCreditCardUseCase(my_val, "MqgRZSGy3cNvhELdceqe").collect {
                Log.d("Credit", it.toString())
            }
        }
    }

    fun onEvent(event: AddCreditCardEvent) {
        viewModelScope.launch {
            when (event) {
                is AddCreditCardEvent.BackButtonPressed -> navigateBack()
                is AddCreditCardEvent.AddCreditCard -> addCreditCard(
                    event.expirationDate,
                    event.cardNumber,
                    event.ccv
                )
            }
        }
    }

    private suspend fun navigateBack() {
        _navigationFlow.emit(AddCreditCardNavigationEvent.NavigateBack)
    }

    private suspend fun addCreditCard(
        expirationDate: String,
        cardNumber: List<String>,
        ccv: String
    ) {
        val uid = dataStoreUseCases.readUserUidUseCase().first()
        addCreditCardUseCase(uid = uid, cardNumber, expirationDate, ccv).collect {
            Log.d("Credit", it.toString())
        }
    }
}

sealed class AddCreditCardNavigationEvent() {
    data object NavigateBack : AddCreditCardNavigationEvent()
}