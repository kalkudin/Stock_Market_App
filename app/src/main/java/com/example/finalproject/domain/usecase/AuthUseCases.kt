package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.usecase.auth_usecase.ForgotPasswordUseCase
import com.example.finalproject.domain.usecase.auth_usecase.GetUserInitialsUseCase
import com.example.finalproject.domain.usecase.auth_usecase.LoginUserUseCase
import com.example.finalproject.domain.usecase.auth_usecase.RegisterUserUseCase
import com.example.finalproject.domain.usecase.auth_usecase.SaveUserInitialsUseCase
import javax.inject.Inject

data class AuthUseCases @Inject constructor(
    val forgotPasswordUseCase: ForgotPasswordUseCase,
    val loginUserUseCase: LoginUserUseCase,
    val registerUserUseCase: RegisterUserUseCase,
    val getUserInitialsUseCase: GetUserInitialsUseCase,
    val saveUserInitialsUseCase: SaveUserInitialsUseCase
)