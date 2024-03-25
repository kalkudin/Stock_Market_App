//package com.example.finalproject.domain.repository.saved_stocks
//
//import com.example.finalproject.data.common.Resource
//import com.example.finalproject.domain.model.company_details.CompanyDetails
//import com.example.finalproject.domain.model.company_list.CompanyList
//import kotlinx.coroutines.flow.Flow
//
//interface SavedStocksRepository {
//    suspend fun getSavedStocks(uid : String) : Flow<Resource<List<CompanyDetails>>>
//    suspend fun removeStockFromFavorites(uid : String, symbols : List<String>) : Flow<Resource<Boolean>>
//}