package com.example.finalproject.data.remote.mapper

import com.example.finalproject.data.remote.model.UserInitialsDataModel
import com.example.finalproject.domain.model.UserInitials

fun UserInitialsDataModel.toDomain() : UserInitials {
    return UserInitials(
        firstName = firstName,
        lastName = lastName
    )
}