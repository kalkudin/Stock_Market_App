package com.example.finalproject.presentation.state.profile

import android.net.Uri
import com.example.finalproject.presentation.model.profile.Transaction
import com.example.finalproject.presentation.model.company_details.CompanyDetails
import com.example.finalproject.presentation.model.credit_card.CreditCard

data class ProfileState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val cardList : List<CreditCard>? = null,
    val transactionList : List<Transaction>? = null,
    val favoriteStockList : List<CompanyDetails>? = null,
    val profileImage : Uri? = null
)