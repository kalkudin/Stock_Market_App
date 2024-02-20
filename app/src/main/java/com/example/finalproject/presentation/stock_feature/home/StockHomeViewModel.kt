package com.example.finalproject.presentation.stock_feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.datastore_usecase.ClearUserSessionUseCase
import com.example.finalproject.domain.usecase.datastore_usecase.ReadUserUidUseCase
import com.example.finalproject.presentation.stock_feature.home.event.StockHomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockHomeViewModel @Inject constructor(
    private val readUserUidUseCase: ReadUserUidUseCase,
    private val clearUserSessionUseCase: ClearUserSessionUseCase
) : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<StockHomeNavigationEvent>()
    val navigationFlow: SharedFlow<StockHomeNavigationEvent> = _navigationFlow.asSharedFlow()
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

    //use this function to get the uid of the user whenever you need it
    //this eliminates the need to get it to the fragment, instead you can read it here directly
    //and pass it to room from the view-model internally
    private suspend fun getFirstUid(): String {
        return readUserUidUseCase().first()
    }
}

sealed class StockHomeNavigationEvent {
    data object LogOut : StockHomeNavigationEvent()
}