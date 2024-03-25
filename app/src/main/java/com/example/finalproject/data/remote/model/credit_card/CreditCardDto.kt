package com.example.finalproject.data.remote.model.credit_card

data class CreditCardDto(
    var id: String = "",
    val cardNumber: String = "",
    val expirationDate: String = "",
    val ccv: String = "",
    val cardType : String = ""
)