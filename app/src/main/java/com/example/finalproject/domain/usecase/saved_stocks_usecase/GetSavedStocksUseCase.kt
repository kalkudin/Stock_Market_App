//package com.example.finalproject.domain.usecase.saved_stocks_usecase
//
//import com.example.finalproject.data.common.Resource
//import com.example.finalproject.domain.model.company_details.CompanyDetails
//import com.example.finalproject.domain.repository.saved_stocks.SavedStocksRepository
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class GetSavedStocksUseCase @Inject constructor(private val savedStocksRepository : SavedStocksRepository) {
//    suspend operator fun invoke(uid : String) : Flow<Resource<List<CompanyDetails>>> {
//        return savedStocksRepository.getSavedStocks(uid = uid)
//    }
//}