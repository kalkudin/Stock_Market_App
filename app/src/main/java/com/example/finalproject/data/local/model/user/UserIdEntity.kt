package com.example.finalproject.data.local.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserIdEntity(
    @PrimaryKey
    val id: String
)