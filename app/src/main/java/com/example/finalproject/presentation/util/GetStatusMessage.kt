package com.example.finalproject.presentation.util

import com.example.finalproject.data.common.StatusType

fun getStatusMessage (statusType: StatusType): String{
    return when(statusType){
        StatusType.TransactionProcessing -> "Transaction is Processing"
    }
}