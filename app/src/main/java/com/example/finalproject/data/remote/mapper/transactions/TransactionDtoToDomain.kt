package com.example.finalproject.data.remote.mapper.transactions

import com.example.finalproject.data.remote.model.transactions.TransactionDto
import com.example.finalproject.domain.model.transactions.GetTransaction

fun TransactionDto.toDomainModel(): GetTransaction {
    return GetTransaction(
        id = this.id,
        amount = this.amount,
        type = this.type,
        date = this.date,
        description = this.description
    )
}