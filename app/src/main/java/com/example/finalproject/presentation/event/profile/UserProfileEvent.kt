package com.example.finalproject.presentation.event.profile

import android.net.Uri
import com.example.finalproject.presentation.model.credit_card.CreditCard

sealed class UserProfileEvent {
    data object NavigateToFavorites : UserProfileEvent()
    data object AddFunds : UserProfileEvent()
    data object UpdateProfile : UserProfileEvent()
    data object ViewTradingHistory : UserProfileEvent()
    data object RetrieveProfileData : UserProfileEvent()
    data class UploadImageToFireBase(val uri: Uri) : UserProfileEvent()
    data class RemoveCard(val creditCard: CreditCard) : UserProfileEvent()
}