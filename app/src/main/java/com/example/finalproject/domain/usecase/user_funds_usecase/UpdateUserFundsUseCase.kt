package com.example.finalproject.domain.usecase.user_funds_usecase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

//class UpdateUserFundsUseCase @Inject constructor(private val userFundsRepository: UserFundsRepository) {
//    suspend operator fun invoke(uid: String, newAmount: Double): Flow<Resource<Boolean>> {
//        Log.d("UpdateUserFundsUseCase", "invoke called with userId: $uid, newAmount: $newAmount")
//        return userFundsRepository.updateUserFunds(uid, newAmount)
//    }
//}

class UpdateUserFundsUseCase @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun invoke(uid: String, newAmount: Double) {
        val userRef = firestore.collection("users").document(uid)

        firestore.runTransaction { transaction ->
            transaction.update(userRef, "amount", newAmount)
        }.addOnSuccessListener {
            Log.d("UpdateUserFundsUseCase", "Transaction success!")
        }.addOnFailureListener { e ->
            Log.w("UpdateUserFundsUseCase", "Transaction failure.", e)
        }
    }
}