package com.example.finalproject.presentation.screen.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.presentation.event.welcome.SessionEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(private val dataStoreUseCases: DataStoreUseCases) : ViewModel(){

    private val _navigationFlow = MutableSharedFlow<SessionNavigationEvent>()
    val navigationFlow : SharedFlow<SessionNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event : SessionEvent) {
        viewModelScope.launch {
            when(event) {
                is SessionEvent.CheckCurrentSession -> checkCurrentSession()
            }
        }
    }

    private suspend fun checkCurrentSession() {
        val sessionExists = dataStoreUseCases.readUserSessionUseCase().first()

        if (sessionExists)
            _navigationFlow.emit(SessionNavigationEvent.SessionExists)
        else
            _navigationFlow.emit(SessionNavigationEvent.SessionMissing)
    }

}

sealed class SessionNavigationEvent() {
    data object SessionExists : SessionNavigationEvent()
    data object SessionMissing : SessionNavigationEvent()
}