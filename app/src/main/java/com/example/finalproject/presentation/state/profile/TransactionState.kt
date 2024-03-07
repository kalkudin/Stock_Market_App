package com.example.finalproject.presentation.state.profile

import com.example.finalproject.presentation.model.profile.Transaction

data class TransactionState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val transactionList : List<Transaction>? = null
)