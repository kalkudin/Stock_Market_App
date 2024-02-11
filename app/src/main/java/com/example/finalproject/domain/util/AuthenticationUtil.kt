package com.example.finalproject.domain.util

import javax.inject.Inject

class AuthenticationUtil @Inject constructor() {
    fun areFieldsNotEmpty(vararg fields: String): Boolean {
        return fields.all { it.isNotEmpty() }
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun doPasswordsMatch(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }
}