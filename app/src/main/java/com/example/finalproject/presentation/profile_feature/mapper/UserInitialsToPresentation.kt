package com.example.finalproject.presentation.profile_feature.mapper

import com.example.finalproject.domain.model.UserInitials
import com.example.finalproject.presentation.profile_feature.model.UserInfo

fun UserInitials.toPresentation() : UserInfo {
    return UserInfo(
        firstName = firstName,
        lastName = lastName
    )
}