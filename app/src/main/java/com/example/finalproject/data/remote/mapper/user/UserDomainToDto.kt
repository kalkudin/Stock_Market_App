package com.example.finalproject.data.remote.mapper.user

import com.example.finalproject.data.remote.model.user.UserDto
import com.example.finalproject.domain.model.user.GetUser

fun GetUser.toDto() : UserDto {
    return UserDto(email = email, password = password)
}