package com.example.finalproject.presentation.mapper.profile

import com.example.finalproject.domain.model.user_initials.GetUserInitials
import com.example.finalproject.presentation.model.profile.UserInfo

fun GetUserInitials.toPresentation() : UserInfo {
    return UserInfo(
        firstName = firstName,
        lastName = lastName
    )
}