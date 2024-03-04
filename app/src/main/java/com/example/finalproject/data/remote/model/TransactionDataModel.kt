package com.example.finalproject.data.remote.model

data class TransactionDataModel(
    val id: String = "",
    val amount: Double = 0.0,
    val type: String = "",
    val date: String = "",
    val description: String = ""
)