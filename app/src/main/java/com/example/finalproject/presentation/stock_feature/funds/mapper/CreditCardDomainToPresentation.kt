package com.example.finalproject.presentation.stock_feature.funds.mapper

import com.example.finalproject.domain.model.CreditCardDomainModel
import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard

fun CreditCardDomainModel.toPresentation() : CreditCard{
    return CreditCard(
        id = id,
        cardNumber = cardNumber,
        expirationDate = expirationDate,
        ccv = ccv,
        cardType = CreditCard.CardType.VISA
    )
}

fun maskAndFormatCardNumber(cardNumber: String): String {
    val maskedNumber = StringBuilder()
    cardNumber.forEachIndexed { index, c ->
        if (c == ' ' && index < 14) {
            maskedNumber.append(' ')
        } else if (index < 14) {
            maskedNumber.append('*')
        } else {
            maskedNumber.append(c)
        }
    }
    return maskedNumber.toString()
}