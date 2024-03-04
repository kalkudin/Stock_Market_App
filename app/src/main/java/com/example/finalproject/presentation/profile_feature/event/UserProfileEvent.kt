package com.example.finalproject.presentation.profile_feature.event

sealed class UserProfileEvent {
    data object NavigateToFavorites : UserProfileEvent()
    data object ManageCreditCards : UserProfileEvent()
    data object AddFunds : UserProfileEvent()
    data object UpdateProfile : UserProfileEvent()
    data object ViewTradingHistory : UserProfileEvent()
}