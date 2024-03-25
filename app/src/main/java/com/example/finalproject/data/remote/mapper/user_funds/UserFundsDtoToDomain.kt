package com.example.finalproject.data.remote.mapper.user_funds

import com.example.finalproject.data.remote.model.user_funds.UserFundsDto
import com.example.finalproject.domain.model.user_funds.GetUserFunds

fun UserFundsDto.toDomain() = GetUserFunds(uid = uid, amount = fundAmount)