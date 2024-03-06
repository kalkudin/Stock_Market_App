package com.example.finalproject.presentation.profile_feature.event

import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard

sealed class UserProfileEvent {
    data object NavigateToFavorites : UserProfileEvent()
    data object AddFunds : UserProfileEvent()
    data object UpdateProfile : UserProfileEvent()
    data object ViewTradingHistory : UserProfileEvent()
    data object RetrieveProfileData : UserProfileEvent()
    data class RemoveCard(val creditCard: CreditCard) : UserProfileEvent()
}