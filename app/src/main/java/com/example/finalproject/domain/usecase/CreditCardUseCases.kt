package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.usecase.credit_card_usecase.AddCreditCardUseCase
import com.example.finalproject.domain.usecase.credit_card_usecase.GetUserCreditCardsUseCase
import com.example.finalproject.domain.usecase.credit_card_usecase.RemoveUserCreditCardUseCase
import javax.inject.Inject
data class CreditCardUseCases @Inject constructor(
    val addCreditCardUseCase: AddCreditCardUseCase,
    val removeUserCreditCardUseCase: RemoveUserCreditCardUseCase,
    val getUserCreditCardsUseCase: GetUserCreditCardsUseCase
)