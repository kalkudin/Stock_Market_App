package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.usecase.user_funds_usecase.AddFundsUseCase
import com.example.finalproject.domain.usecase.user_funds_usecase.AddUserFundsUseCase
import com.example.finalproject.domain.usecase.user_funds_usecase.RemoveUserFundsUseCase
import com.example.finalproject.domain.usecase.user_funds_usecase.RetrieveUserFundsUseCase
import com.example.finalproject.domain.usecase.user_funds_usecase.SubtractFundsUseCase
import com.example.finalproject.domain.usecase.user_funds_usecase.UpdateUserFundsUseCase
import javax.inject.Inject

data class UserFundsUseCases @Inject constructor(
    val addUserFundsUseCase: AddUserFundsUseCase,
    val removeUserFundsUseCase: RemoveUserFundsUseCase,
    val retrieveUserFundsUseCase: RetrieveUserFundsUseCase,
    //
    val updateUserFundsUseCase: UpdateUserFundsUseCase,
    val subtractFundsUseCase: SubtractFundsUseCase,
    val addFundsUseCase: AddFundsUseCase
)