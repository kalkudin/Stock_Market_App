package com.example.finalproject.presentation.mapper.profile

import com.example.finalproject.domain.model.transactions.GetTransaction
import com.example.finalproject.presentation.model.profile.Transaction
import java.util.Locale

fun GetTransaction.toPresentation() : Transaction {
    return Transaction(
        id = id,
        amount = amount,
        date = date,
        description = description,
        type = when (type.lowercase(Locale.ROOT)) {
            "outgoing" -> Transaction.TransactionType.OUTGOING
            "internal" -> Transaction.TransactionType.INTERNAL
            else -> Transaction.TransactionType.UNSPECIFIED
        }
    )
}