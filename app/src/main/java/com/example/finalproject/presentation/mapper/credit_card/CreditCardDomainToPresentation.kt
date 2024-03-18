package com.example.finalproject.presentation.mapper.credit_card

import com.example.finalproject.domain.model.credit_card.GetCreditCard
import com.example.finalproject.presentation.model.credit_card.CreditCard
import java.util.Locale

fun GetCreditCard.toPresentation() : CreditCard {
    return CreditCard(
        id = id,
        cardNumber = cardNumber,
        expirationDate = expirationDate,
        ccv = ccv,
        cardType = when(type.lowercase(Locale.ROOT)) {
            "visa" -> CreditCard.CardType.VISA
            "mastercard" -> CreditCard.CardType.MASTER_CARD
            else -> CreditCard.CardType.UNKNOWN
        }
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