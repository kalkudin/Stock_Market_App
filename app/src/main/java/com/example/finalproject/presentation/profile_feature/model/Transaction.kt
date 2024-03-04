package com.example.finalproject.presentation.profile_feature.model

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
        UNSPECIFIED
    }
}