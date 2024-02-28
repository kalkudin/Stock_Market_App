package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.usecase.user_funds_usecase.AddUserFundsUseCase
import com.example.finalproject.domain.usecase.user_funds_usecase.RemoveUserFundsUseCase
import com.example.finalproject.domain.usecase.user_funds_usecase.RetrieveUserFundsUseCase
import javax.inject.Inject

data class UserFundsUseCases @Inject constructor(
    val addUserFundsUseCase: AddUserFundsUseCase,
    val removeUserFundsUseCase: RemoveUserFundsUseCase,
    val retrieveUserFundsUseCase: RetrieveUserFundsUseCase
)