package com.example.finalproject.domain.util

import android.util.Log
import androidx.paging.LOGGER
import java.util.UUID
import java.util.Calendar
import javax.inject.Inject

class CreditCardValidationUtil @Inject constructor() {

    fun validateCreditCardNumber(cardNumber: String): Boolean {
        val cleanedCardNumber = cardNumber.replace(" ", "")
        return cleanedCardNumber.length == 16 && cleanedCardNumber.all { it.isDigit() }
    }

    fun validateDate(expiryDate: String): Boolean {
        try {
            val parts = expiryDate.split("/")
            if (parts.size != 2) return false

            val month = parts[0].toInt()
            val year = parts[1].toInt() + 2000

            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH) + 1

            if (month !in 1..12) return false

            if (year < currentYear) return false

            return !(year == currentYear && month < currentMonth)
        } catch (e: Exception) {
            return false
        }
    }

    fun validateCcv(ccv: String): Boolean {
        return ccv.length in 3..4 && ccv.all { it.isDigit() }
    }

    fun generateUniqueId(): String = UUID.randomUUID().toString()
}