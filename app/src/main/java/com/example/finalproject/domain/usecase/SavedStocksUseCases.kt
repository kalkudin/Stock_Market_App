package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.usecase.saved_stocks_usecase.GetSavedStocksUseCase
import com.example.finalproject.domain.usecase.saved_stocks_usecase.RemoveSavedStockUseCase
import javax.inject.Inject

data class SavedStocksUseCases @Inject constructor(
    val getSavedStocksUseCase: GetSavedStocksUseCase,
    val removeSavedStockUseCase: RemoveSavedStockUseCase
)