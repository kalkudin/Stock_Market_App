package com.example.finalproject.data.local.mapper.user

import com.example.finalproject.data.local.model.user.UserIdEntity
import com.example.finalproject.domain.model.user.GetUserId

fun UserIdEntity.toDomain(): GetUserId {
    return GetUserId(
        id = id
    )
}

fun GetUserId.toData(): UserIdEntity {
    return UserIdEntity(
        id = id
    )
}