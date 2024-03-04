package com.example.finalproject.domain.usecase.transactions_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.GetTransaction
import com.example.finalproject.domain.repository.TransactionsRepository
import com.example.finalproject.domain.util.CreditCardValidationUtil
import com.example.finalproject.domain.util.DateUtil
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveTransactionUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val validationUtil: CreditCardValidationUtil,
    private val dateUtil: DateUtil
) {
    suspend operator fun invoke(uid: String, amount: Double, type: String, description : String): Flow<Resource<Boolean>> {
        return transactionsRepository.saveTransaction(
            uid = uid, transaction =
            GetTransaction(
                id = validationUtil.generateUniqueId(),
                date = dateUtil.getCurrentDate(),
                amount = amount,
                type = type,
                description = description
            )
        )
    }
}