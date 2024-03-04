package com.example.finalproject.presentation.profile_feature.mapper

import com.example.finalproject.domain.model.GetTransaction
import com.example.finalproject.presentation.profile_feature.model.Transaction
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