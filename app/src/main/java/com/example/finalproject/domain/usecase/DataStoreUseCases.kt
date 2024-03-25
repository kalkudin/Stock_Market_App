package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.usecase.datastore_usecase.ClearUserSessionUseCase
import com.example.finalproject.domain.usecase.datastore_usecase.ReadUserSessionUseCase
import com.example.finalproject.domain.usecase.datastore_usecase.ReadUserUidUseCase
import com.example.finalproject.domain.usecase.datastore_usecase.SaveUserSessionUseCase
import com.example.finalproject.domain.usecase.datastore_usecase.SaveUserUidUseCase
import javax.inject.Inject

data class DataStoreUseCases @Inject constructor(
    val clearUserSessionUseCase: ClearUserSessionUseCase,
    val readUserSessionUseCase: ReadUserSessionUseCase,
    val readUserUidUseCase : ReadUserUidUseCase,
    val saveUserSessionUseCase: SaveUserSessionUseCase,
    val saveUserUidUseCase: SaveUserUidUseCase
)