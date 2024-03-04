package com.example.finalproject.domain.repository

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.GetTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {
    suspend fun getTransactions(uid : String) : Flow<Resource<List<GetTransaction>>>
    suspend fun saveTransaction(uid : String, transaction : GetTransaction) : Flow<Resource<Boolean>>
}