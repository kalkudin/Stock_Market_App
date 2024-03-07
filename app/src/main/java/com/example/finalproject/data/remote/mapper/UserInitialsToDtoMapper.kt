package com.example.finalproject.data.remote.mapper

import com.example.finalproject.data.remote.model.UserInitialsDataModel
import com.example.finalproject.domain.model.User
import com.example.finalproject.domain.model.UserInitials

fun UserInitials.toData() : UserInitialsDataModel {
    return UserInitialsDataModel(
        firstName = firstName,
        lastName = lastName,
    )
}