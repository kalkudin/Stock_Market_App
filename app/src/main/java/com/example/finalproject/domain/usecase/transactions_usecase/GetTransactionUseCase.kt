package com.example.finalproject.domain.usecase.transactions_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.transactions.GetTransaction
import com.example.finalproject.domain.repository.firestore.transactions.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(private val transactionsRepository: TransactionsRepository) {
    suspend operator fun invoke(uid : String) : Flow<Resource<List<GetTransaction>>> {
        return transactionsRepository.getTransactions(uid = uid)
    }
}