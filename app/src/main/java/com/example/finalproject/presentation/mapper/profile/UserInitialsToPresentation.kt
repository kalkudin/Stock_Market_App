package com.example.finalproject.presentation.mapper.profile

import com.example.finalproject.domain.model.UserInitials
import com.example.finalproject.presentation.model.profile.UserInfo

fun UserInitials.toPresentation() : UserInfo {
    return UserInfo(
        firstName = firstName,
        lastName = lastName
    )
}