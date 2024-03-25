package com.example.finalproject.presentation.extension

import android.text.InputType
import android.view.MotionEvent
import android.widget.EditText
import com.example.finalproject.R

fun EditText.setupPasswordToggle() {
    val endIcon = 2
    setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP &&
            event.rawX >= (right - (compoundDrawablesRelative[endIcon]?.bounds?.width() ?: 0))
        ) {
            togglePasswordVisibility()
            performClick()
            return@setOnTouchListener true
        }
        return@setOnTouchListener false
    }
}

private fun EditText.togglePasswordVisibility() {
    val isPasswordVisible = inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    setCompoundDrawablesRelativeWithIntrinsicBounds(
        0, 0,
        if (isPasswordVisible) R.drawable.ic_lock_locked else R.drawable.ic_lock_unlocked, 0
    )
    inputType = if (isPasswordVisible) {
        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    } else {
        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    }
    setSelection(text.length)
}