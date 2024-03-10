package com.example.finalproject.presentation.model.funds

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class CreditCard(
    val id : String = "",
    val cardNumber: String,
    val expirationDate : String,
    val ccv : String,
    val cardType : CardType
) : Parcelable {
    enum class CardType {
        VISA,
        MASTER_CARD,
        UNKNOWN
    }
}