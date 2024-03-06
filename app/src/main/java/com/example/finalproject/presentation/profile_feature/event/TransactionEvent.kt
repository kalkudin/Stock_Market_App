package com.example.finalproject.presentation.profile_feature.event

sealed class TransactionEvent {
    data object NavigateBack : TransactionEvent()
    data object GetTransactions : TransactionEvent()
    data class SortTransactions(val sort : String) : TransactionEvent()
}