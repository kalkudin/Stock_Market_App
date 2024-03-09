package com.example.finalproject.presentation.mapper.home

import com.example.finalproject.domain.model.UserInitials
import java.util.Locale

fun UserInitials.formatFirstName() : String {
    return "Hey ${firstName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }}"
}