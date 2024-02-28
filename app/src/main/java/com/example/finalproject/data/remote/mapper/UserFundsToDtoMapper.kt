package com.example.finalproject.data.remote.mapper

import com.example.finalproject.data.remote.model.UserFundsDto
import com.example.finalproject.domain.model.UserFunds

fun UserFunds.toDto() = UserFundsDto(uid = uid, fundAmount = amount)