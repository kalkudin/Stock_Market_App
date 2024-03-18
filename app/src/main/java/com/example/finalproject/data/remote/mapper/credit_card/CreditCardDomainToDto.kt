package com.example.finalproject.data.remote.mapper.credit_card

import com.example.finalproject.data.remote.model.credit_card.CreditCardDto
import com.example.finalproject.domain.model.credit_card.GetCreditCard

fun CreditCardDto.toDomainModel(): GetCreditCard = GetCreditCard(
    id = id,
    cardNumber = cardNumber,
    expirationDate = expirationDate,
    ccv = ccv,
    type = cardType
)