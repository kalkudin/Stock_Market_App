package com.example.finalproject.presentation.stock_feature.funds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.presentation.stock_feature.funds.event.UserFundsEvent
import com.example.finalproject.presentation.stock_feature.ones_to_watch_extensive_list.ExtensiveStocksNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserFundsViewModel @Inject constructor() : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<FundsNavigationEvent>()
    val navigationFlow : SharedFlow<FundsNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event: UserFundsEvent) {
        viewModelScope.launch {
            when(event) {
                is UserFundsEvent.BackButtonPressed -> navigateBack()
            }
        }
    }

    private suspend fun navigateBack() {
        _navigationFlow.emit(FundsNavigationEvent.NavigateBack)
    }
}

sealed class FundsNavigationEvent() {
    data object NavigateBack : FundsNavigationEvent()
}