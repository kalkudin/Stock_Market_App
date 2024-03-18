package com.example.finalproject.domain.usecase.transactions_usecase

import android.util.Log
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.transactions.GetTransaction
import com.example.finalproject.domain.repository.firestore.transactions.TransactionsRepository
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
        Log.d("SaveTransactionUseCase", "Saving transaction. User ID: $uid, Amount: $amount, Type: $type, Description: $description")
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
        Log.d("SaveTransactionUseCase", "Transaction saved successfully.")
    }
}