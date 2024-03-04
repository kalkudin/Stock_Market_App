package com.example.finalproject.domain.usecase.saved_stocks_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.repository.saved_stocks.SavedStocksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveSavedStockUseCase @Inject constructor(private val savedStocksRepository : SavedStocksRepository) {
    suspend operator fun invoke(uid : String, symbols : List<String>) : Flow<Resource<Boolean>> {
        return savedStocksRepository.removeStockFromFavorites(uid = uid, symbols = symbols)
    }
}