package com.example.finalproject.presentation.stock_feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.datastore_usecase.ClearUserSessionUseCase
import com.example.finalproject.domain.usecase.datastore_usecase.ReadUserSessionUseCase
import com.example.finalproject.presentation.stock_feature.event.StockHomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockHomeViewModel @Inject constructor(
    private val readUserSessionUseCase: ReadUserSessionUseCase,
    private val clearUserSessionUseCase: ClearUserSessionUseCase
) : ViewModel() {

    private val _uidFlow = MutableSharedFlow<String>()
    val uidFlow: SharedFlow<String> = _uidFlow.asSharedFlow()

    private val _navigationFlow = MutableSharedFlow<StockHomeNavigationEvent>()
    val navigationFlow: SharedFlow<StockHomeNavigationEvent> = _navigationFlow.asSharedFlow()

    fun getUidFromDataStore() = readUserSessionUseCase()

    fun onEvent(event: StockHomeEvent) {
        when (event) {
            is StockHomeEvent.LogOut -> logOut()
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            clearUserSessionUseCase()
            _navigationFlow.emit(StockHomeNavigationEvent.LogOut)
        }
    }
}

sealed class StockHomeNavigationEvent {
    data object LogOut : StockHomeNavigationEvent()
}