package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.usecase.transactions_usecase.GetTransactionUseCase
import com.example.finalproject.domain.usecase.transactions_usecase.SaveTransactionUseCase
import javax.inject.Inject

data class TransactionsUseCases @Inject constructor(
    val getTransactionsUseCase : GetTransactionUseCase,
    val saveTransactionUseCase: SaveTransactionUseCase
)