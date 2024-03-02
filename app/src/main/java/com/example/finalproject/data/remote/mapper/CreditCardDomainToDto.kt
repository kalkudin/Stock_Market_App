package com.example.finalproject.data.remote.mapper

import com.example.finalproject.data.remote.model.CreditCardDataModel
import com.example.finalproject.domain.model.CreditCardDomainModel

fun CreditCardDataModel.toDomainModel(): CreditCardDomainModel = CreditCardDomainModel(
    id = id,
    cardNumber = cardNumber,
    expirationDate = expirationDate,
    ccv = ccv
)