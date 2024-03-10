package com.example.finalproject.data.local.mapper.user

import com.example.finalproject.data.local.model.user.UserIdEntity
import com.example.finalproject.domain.model.user.UserId

fun UserIdEntity.toDomain(): UserId {
    return UserId(
        id = id
    )
}

fun UserId.toData(): UserIdEntity {
    return UserIdEntity(
        id = id
    )
}