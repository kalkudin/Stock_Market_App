package com.example.finalproject.domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateUtil @Inject constructor() {
    fun getCurrentDate(format: String = "yyyy-MM-dd"): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern(format)
        return currentDate.format(formatter)
    }
}