package com.example.finalproject.domain.usecase.user_funds_usecase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

//class SubtractFundsUseCase @Inject constructor(
//    private val retrieveUserFundsUseCase: RetrieveUserFundsUseCase,
//    private val updateUserFundsUseCase: UpdateUserFundsUseCase
//) {
//    suspend fun subtractFunds(uid: String, amount: Double) {
//        Log.d("SubtractFundsUseCase", "subtractFunds called with userId: $uid, amount: $amount")
//        // Retrieve the current user funds
//        retrieveUserFundsUseCase(uid).collect { currentUserFundsResource ->
//            Log.d("SubtractFundsUseCase", "retrieveUserFundsUseCase collected")
//            if (currentUserFundsResource is Resource.Success) {
//                val currentUserFunds = currentUserFundsResource.data
//                val currentAmount = currentUserFunds.amount
//
//                // Subtract the amount from the current user funds
//                val updatedAmount = currentAmount - amount
//                Log.d("SubtractFundsUseCase", "Subtraction performed, updatedAmount: $updatedAmount")
//
//                // Update the user funds in the database
//                updateUserFundsUseCase(uid, updatedAmount)
//                Log.d("SubtractFundsUseCase", "updateUserFundsUseCase called with userId: $uid, updatedAmount: $updatedAmount")
//            } else {
//                flowOf(currentUserFundsResource)
//            }
//        }
//    }
//}
class SubtractFundsUseCase @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun subtractFunds(uid: String, amount: Double) {
        Log.d("SubtractFundsUseCase", "subtractFunds called with userId: $uid, amount: $amount")
        val userFundsRef = firestore.collection("userFunds").document(uid)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(userFundsRef)
            val currentAmount = snapshot.getDouble("amount")

            if (currentAmount != null && currentAmount >= amount) {
                val newAmount = currentAmount - amount
                transaction.update(userFundsRef, "amount", newAmount)
                Log.d("SubtractFundsUseCase", "Subtraction performed, updatedAmount: $newAmount")
            } else {
                throw Exception("Insufficient funds")
            }
        }.addOnSuccessListener {
            Log.d("SubtractFundsUseCase", "Transaction success!")
        }.addOnFailureListener { e ->
            Log.w("SubtractFundsUseCase", "Transaction failure.", e)
        }
    }
}