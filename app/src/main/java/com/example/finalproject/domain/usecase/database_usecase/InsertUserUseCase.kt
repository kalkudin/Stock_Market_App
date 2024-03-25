package com.example.finalproject.domain.usecase.database_usecase

import com.example.finalproject.domain.model.user.GetUserId
import com.example.finalproject.domain.repository.watchlisted_stocks.WatchlistedStocksRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: WatchlistedStocksRepository
) {
    suspend operator fun invoke(user: GetUserId) {
        repository.insertUser(user)
    }
}