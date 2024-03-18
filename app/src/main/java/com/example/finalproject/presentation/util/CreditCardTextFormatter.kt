package com.example.finalproject.presentation.util

import android.text.Editable
import android.text.TextWatcher

class CreditCardNumberFormattingTextWatcher : TextWatcher {

    private val spaceInterval = 5
    private val space = ' '

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        s?.let {
            if (it.isNotEmpty() && (it.length % spaceInterval == 0)) {
                val lastChar = it[it.length - 1]
                if (space == lastChar) {
                    it.delete(it.length - 1, it.length)
                } else {
                    it.insert(it.length - 1, space.toString())
                }
            }
        }
    }
}