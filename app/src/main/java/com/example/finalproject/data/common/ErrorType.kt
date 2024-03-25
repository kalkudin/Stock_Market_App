package com.example.finalproject.data.common

sealed class ErrorType {
    //authentication exceptions
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
    //http request exceptions
    data object SocketTimeout:ErrorType()
    data object UnknownHost:ErrorType()
    data object SSLHandshake:ErrorType()
    data object Http:ErrorType()
    data object IO:ErrorType()
    data object InvalidCardNumber : ErrorType()
    data object InvalidExpirationDate : ErrorType()
    data object InvalidCCV : ErrorType()
    //card errors
    data object CardAlreadyExists : ErrorType()
    data object NoSuchCreditCard : ErrorType()
    data object InvalidFunds : ErrorType()
    data object InvalidCardType : ErrorType()
    //room database errors
    data object LocalDatabaseError : ErrorType()
    data object LocalDatabaseEmpty : ErrorType()
    data object LocalDatabaseErrorDelete : ErrorType()
    //funds errors
    data object InsufficientFunds : ErrorType()
    data object InsufficientStocks : ErrorType()
    //buy/sell errors
    data object AmountGreaterThanZeroToBuy : ErrorType()
    data object AmountGreaterThanZeroToSell : ErrorType()
}