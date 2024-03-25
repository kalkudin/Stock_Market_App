package com.example.finalproject.data.remote.mapper.user_initials

import com.example.finalproject.data.remote.model.user_initials.UserInitialsDto
import com.example.finalproject.domain.model.user_initials.GetUserInitials

fun UserInitialsDto.toDomain() : GetUserInitials {
    return GetUserInitials(
        firstName = firstName,
        lastName = lastName
    )
}