package com.example.finalproject.presentation.stock_feature.funds.model

data class CreditCard(
    val id : String,
    val cardNumber: String,
    val expirationDate : String,
    val ccv : String,
    val cardType : CardType
) {
    enum class CardType {
        VISA,
        MASTER_CARD
    }
}