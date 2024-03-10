package com.example.finalproject.domain.usecase.database_usecase


import com.example.finalproject.domain.model.company_details.CompanyDetails
import com.example.finalproject.domain.model.user.UserId
import com.example.finalproject.domain.repository.watchlisted_stocks.WatchlistedStocksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchlistedStocksForUserUseCase @Inject constructor(
    private val repository: WatchlistedStocksRepository
) {
    suspend operator fun invoke(user: UserId): Flow<List<CompanyDetails>> {
        return repository.getFavouriteStocksForUser(user)
    }
}