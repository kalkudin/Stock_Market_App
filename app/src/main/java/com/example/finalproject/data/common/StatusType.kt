package com.example.finalproject.data.common

sealed class StatusType {
    data object TransactionProcessing : StatusType()
}