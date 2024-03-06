package com.example.finalproject.domain.model

data class CreditCardDomainModel(
    val id: String,
    val cardNumber: String,
    val expirationDate: String,
    val ccv: String,
    val type : String
)