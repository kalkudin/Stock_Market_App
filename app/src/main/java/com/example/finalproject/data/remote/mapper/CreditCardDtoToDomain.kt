package com.example.finalproject.data.remote.mapper

import com.example.finalproject.data.remote.model.CreditCardDataModel
import com.example.finalproject.domain.model.CreditCardDomainModel

fun CreditCardDomainModel.toDataModel(): CreditCardDataModel = CreditCardDataModel(
    id = id,
    cardNumber = cardNumber,
    expirationDate = expirationDate,
    ccv = ccv,
    cardType = type
)