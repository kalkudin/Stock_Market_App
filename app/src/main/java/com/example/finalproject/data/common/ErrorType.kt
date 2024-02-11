package com.example.finalproject.data.common

sealed class ErrorType {
    data object WeakPassword : ErrorType()
    data object InvalidCredentials : ErrorType()
    data object UserCollision : ErrorType()
    data object UserNotFound : ErrorType()
    data object EmailError : ErrorType()
    data object NetworkError : ErrorType()
    data object FieldsEmpty : ErrorType()
    data object InvalidEmail : ErrorType()
    data object PasswordCriteriaNotMet : ErrorType()
    data object PasswordsNotMatching : ErrorType()
    data class UnknownError(val message: String) : ErrorType()
}