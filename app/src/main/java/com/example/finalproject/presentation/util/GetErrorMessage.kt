package com.example.finalproject.presentation.util

import com.example.finalproject.data.common.ErrorType

fun getErrorMessage(errorType: ErrorType): String {
    return when (errorType) {
        ErrorType.WeakPassword -> "The password is too weak."
        ErrorType.InvalidCredentials -> "Invalid credentials provided."
        ErrorType.UserCollision -> "An account with this email already exists."
        ErrorType.UserNotFound -> "No user found with this email."
        ErrorType.EmailError -> "There was an error with the email."
        ErrorType.NetworkError -> "Check your internet connection and try again."
        ErrorType.FieldsEmpty -> "Please fill in all fields."
        ErrorType.InvalidEmail -> "Invalid email address."
        ErrorType.PasswordCriteriaNotMet -> "Password does not meet the criteria."
        ErrorType.PasswordsNotMatching -> "The passwords do not match."
        //
        ErrorType.SocketTimeout -> "The server did not respond in time."
        ErrorType.UnknownHost -> "The server could not be found."
        ErrorType.SSLHandshake -> "There was an error establishing a secure connection."
        ErrorType.Http -> "There was an error with the HTTP request."
        ErrorType.IO -> "There was an input/output error."
        //
        ErrorType.InvalidCCV -> "Invalid CCV"
        ErrorType.InvalidCardNumber -> "Invalid card number"
        ErrorType.InvalidExpirationDate -> "Invalid expiration date"
        ErrorType.CardAlreadyExists -> "This card already exists"
        ErrorType.NoSuchCreditCard -> "This Credit Card Does Not Exist"
        ErrorType.InvalidFunds -> "The amount entered is invalid, please try again"
        //
        ErrorType.LocalDatabaseError -> "There was an error with the local database."
        ErrorType.LocalDatabaseEmpty -> "The local database is empty."
        ErrorType.LocalDatabaseErrorDelete -> "There was an error deleting from the local database."

        is ErrorType.UnknownError -> errorType.message
    }
}