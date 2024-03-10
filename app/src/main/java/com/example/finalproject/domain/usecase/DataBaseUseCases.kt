package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.usecase.database_usecase.DeleteWatchlistedStocksUseCase
import com.example.finalproject.domain.usecase.database_usecase.GetWatchlistedStocksForUserUseCase
import com.example.finalproject.domain.usecase.database_usecase.InsertStocksUseCase
import com.example.finalproject.domain.usecase.database_usecase.InsertUserUseCase
import com.example.finalproject.domain.usecase.database_usecase.InsertWatchlistedStocksUseCase
import javax.inject.Inject

data class DataBaseUseCases @Inject constructor(
    val deleteWatchlistedStocksUseCase: DeleteWatchlistedStocksUseCase,
    val getWatchlistedStocksForUserUseCase: GetWatchlistedStocksForUserUseCase,
    val insertUserUseCase: InsertUserUseCase,
    val insertStocksUseCase: InsertStocksUseCase,
    val insertWatchlistedStocksUseCase: InsertWatchlistedStocksUseCase
)
