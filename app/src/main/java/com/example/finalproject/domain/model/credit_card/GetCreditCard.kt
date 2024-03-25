package com.example.finalproject.domain.model.credit_card

data class GetCreditCard(
    val id: String,
    val cardNumber: String,
    val expirationDate: String,
    val ccv: String,
    val type : String
)