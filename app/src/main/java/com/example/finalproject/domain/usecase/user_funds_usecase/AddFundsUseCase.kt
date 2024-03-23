package com.example.finalproject.domain.usecase.user_funds_usecase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

//class AddFundsUseCase @Inject constructor(
//    private val retrieveUserFundsUseCase: RetrieveUserFundsUseCase,
//    private val updateUserFundsUseCase: UpdateUserFundsUseCase
//) {
//    suspend fun addFunds(uid: String, amount: Double) {
//        Log.d("AddFundsUseCase", "addFunds called with userId: $uid, amount: $amount")
//        // Retrieve the current user funds
//        retrieveUserFundsUseCase(uid).collect { currentUserFundsResource ->
//            Log.d("AddFundsUseCase", "retrieveUserFundsUseCase collected")
//            if (currentUserFundsResource is Resource.Success) {
//                val currentUserFunds = currentUserFundsResource.data
//                val currentAmount = currentUserFunds.amount
//
//                // Add the amount to the current user funds
//                val updatedAmount = currentAmount + amount
//                Log.d("AddFundsUseCase", "Addition performed, updatedAmount: $updatedAmount")
//
//                // Update the user funds in the database
//                updateUserFundsUseCase(uid, updatedAmount)
//                Log.d("AddFundsUseCase", "updateUserFundsUseCase called with userId: $uid, updatedAmount: $updatedAmount")
//            } else {
//                flowOf(currentUserFundsResource)
//            }
//        }
//    }
//}
class AddFundsUseCase @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun addFunds(uid: String, amount: Double) {
        val userRef = firestore.collection("users").document(uid)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val currentAmount = snapshot.getDouble("amount")

            val newAmount = if (currentAmount != null) {
                currentAmount + amount
            } else {
                amount
            }

            transaction.set(userRef, mapOf("amount" to newAmount))
            Log.d("AddFundsUseCase", "Addition performed, updatedAmount: $newAmount")
        }.addOnSuccessListener {
            Log.d("AddFundsUseCase", "Transaction success!")
        }.addOnFailureListener { e ->
            Log.w("AddFundsUseCase", "Transaction failure.", e)
        }
    }
}