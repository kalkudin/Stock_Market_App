package com.example.finalproject.presentation.model.bottom_sheets

import com.example.finalproject.presentation.model.credit_card.CreditCard

data class NewCardType(
    val path : Int,
    val isSelected : Boolean = false,
    val type : CreditCard.CardType
)