package com.example.finalproject.data.remote.mapper

import com.example.finalproject.data.remote.model.TransactionDataModel
import com.example.finalproject.domain.model.GetTransaction

fun GetTransaction.toDataModel(): TransactionDataModel {
    return TransactionDataModel(
        id = this.id,
        amount = this.amount,
        type = this.type,
        date = this.date,
        description = this.description
    )
}