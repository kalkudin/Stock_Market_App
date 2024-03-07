package com.example.finalproject.presentation.state.profile

import com.example.finalproject.presentation.model.profile.Transaction
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel
import com.example.finalproject.presentation.model.funds.CreditCard

data class ProfileState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val cardList : List<CreditCard>? = null,
    val transactionList : List<Transaction>? = null,
    val favoriteStockList : List<CompanyDetailsModel>? = null
)