package com.example.finalproject.data.repository

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.company_details.CompanyDetails
import com.example.finalproject.domain.repository.saved_stocks.SavedStocksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavedStocksRepositoryImpl @Inject constructor() : SavedStocksRepository{

    private val mockDataList = listOf(
        CompanyDetails(symbol = "AAPL", companyName = "Apple Inc.", description = "Designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories worldwide."),
        CompanyDetails(symbol = "GOOGL", companyName = "Alphabet Inc.", description = "Provides online advertising services in the United States, Europe, the Middle East, Africa, the Asia-Pacific, Canada, and Latin America."),
        CompanyDetails(symbol = "MSFT", companyName = "Microsoft Corporation", description = "Develops, licenses, and supports software, services, devices, and solutions worldwide."),
        CompanyDetails(symbol = "AMZN", companyName = "Amazon.com, Inc.", description = "Engages in the retail sale of consumer products and subscriptions in North America and internationally."),
        CompanyDetails(symbol = "TSLA", companyName = "Tesla, Inc.", description = "Designs, develops, manufactures, leases, and sells electric vehicles, and energy generation and storage systems in the United States and internationally.")
    )

    override suspend fun getSavedStocks(uid: String): Flow<Resource<List<CompanyDetails>>> {
        return flow {
            emit(Resource.Success(data = mockDataList))
        }
    }

    override suspend fun removeStockFromFavorites(uid: String, symbols: List<String>): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Success(data = true))
        }
    }
}