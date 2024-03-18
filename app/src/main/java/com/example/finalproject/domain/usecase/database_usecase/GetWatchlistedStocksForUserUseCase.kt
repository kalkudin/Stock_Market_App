package com.example.finalproject.domain.usecase.database_usecase


import com.example.finalproject.domain.model.company_details.GetCompanyDetails
import com.example.finalproject.domain.model.user.GetUserId
import com.example.finalproject.domain.repository.watchlisted_stocks.WatchlistedStocksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchlistedStocksForUserUseCase @Inject constructor(
    private val repository: WatchlistedStocksRepository
) {
    suspend operator fun invoke(user: GetUserId): Flow<List<GetCompanyDetails>> {
        return repository.getFavouriteStocksForUser(user)
    }
}