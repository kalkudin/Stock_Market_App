package com.example.finalproject.presentation.profile_feature.state

import com.example.finalproject.presentation.profile_feature.model.Transaction
import com.example.finalproject.presentation.stock_feature.company_details.model.CompanyDetailsModel
import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard

data class ProfileState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val cardList : List<CreditCard>? = null,
    val transactionList : List<Transaction>? = null,
    val favoriteStockList : List<CompanyDetailsModel>? = null
)