package com.example.finalproject.data.common

sealed class SuccessType {
    data object TransactionSuccessful : SuccessType()
}