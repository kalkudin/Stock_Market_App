package com.example.finalproject.domain.model.transactions

data class GetTransaction(
    val id: String,
    val amount: Double,
    val type: String,
    val date: String,
    val description: String
)