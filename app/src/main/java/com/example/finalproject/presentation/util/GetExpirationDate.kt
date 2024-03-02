package com.example.finalproject.presentation.util

import android.widget.EditText

fun formatExpirationDate(monthEditText: EditText, yearEditText: EditText): String {
    val month = monthEditText.text.toString()
    val year = yearEditText.text.toString()
    return "$month/$year"
}