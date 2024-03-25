package com.example.finalproject.presentation.state.funds

import com.example.finalproject.presentation.model.profile.UserInfo

data class FundsState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val userInitials : String? = null,
    val success : Boolean = false
)