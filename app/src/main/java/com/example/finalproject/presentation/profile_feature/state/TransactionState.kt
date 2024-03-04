package com.example.finalproject.presentation.profile_feature.state

import com.example.finalproject.presentation.profile_feature.model.Transaction

data class TransactionState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val transactionList : List<Transaction>? = null
)