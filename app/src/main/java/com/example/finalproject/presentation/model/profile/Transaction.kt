package com.example.finalproject.presentation.model.profile

data class Transaction(
    val id: String,
    val amount: Double,
    val date: String,
    val description: String,
    val type: TransactionType,
    ) {
    enum class TransactionType {
        OUTGOING,
        INTERNAL,
        BUY,
        SELL,
        UNSPECIFIED
    }
}