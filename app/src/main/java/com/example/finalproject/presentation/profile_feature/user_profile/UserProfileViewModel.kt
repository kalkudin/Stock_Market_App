package com.example.finalproject.presentation.profile_feature.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.presentation.profile_feature.event.UserProfileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor() : ViewModel(){

    private val _navigationFlow = MutableSharedFlow<UserProfileNavigationEvent>()
    val navigationFlow : SharedFlow<UserProfileNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event: UserProfileEvent){
        viewModelScope.launch {
            when(event) {
                is UserProfileEvent.AddFunds -> {
                    navigateToAddFunds()
                }
                is UserProfileEvent.ManageCreditCards -> {
                    manageCreditCards()
                }
                is UserProfileEvent.NavigateToFavorites -> {
                    navigateToFavorites()
                }
                is UserProfileEvent.UpdateProfile -> {
                    updateProfile()
                }
                is UserProfileEvent.ViewTradingHistory -> {
                    viewTradingHistory()
                }
            }
        }
    }

    private suspend fun navigateToAddFunds() {
        _navigationFlow.emit(UserProfileNavigationEvent.NavigateToAddFunds)
    }

    private suspend fun manageCreditCards() {
        _navigationFlow.emit(UserProfileNavigationEvent.OpenCreditCardManager)
    }

    private suspend fun updateProfile() {
        _navigationFlow.emit(UserProfileNavigationEvent.UpdateProfile)
    }

    private suspend fun viewTradingHistory() {
        _navigationFlow.emit(UserProfileNavigationEvent.OpenTradingHistory)
    }

    private suspend fun navigateToFavorites() {
        _navigationFlow.emit(UserProfileNavigationEvent.NavigateToFavorites)
    }
}

sealed class UserProfileNavigationEvent {
    data object NavigateToAddFunds : UserProfileNavigationEvent()
    data object OpenCreditCardManager : UserProfileNavigationEvent()
    data object NavigateToFavorites : UserProfileNavigationEvent()
    data object UpdateProfile : UserProfileNavigationEvent()
    data object OpenTradingHistory : UserProfileNavigationEvent()
}