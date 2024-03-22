package com.example.finalproject.presentation.util

import com.example.finalproject.data.common.SuccessType

fun getSuccessMessage(successType: SuccessType): String{
    return when(successType){
        SuccessType.TransactionSuccessful -> "Transaction is Successful"
    }
}