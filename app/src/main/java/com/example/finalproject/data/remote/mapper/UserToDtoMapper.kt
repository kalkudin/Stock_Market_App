package com.example.finalproject.data.remote.mapper

import com.example.finalproject.data.remote.model.UserDto
import com.example.finalproject.domain.model.User

fun User.toDto() : UserDto {
    return UserDto(email = email, password = password)
}