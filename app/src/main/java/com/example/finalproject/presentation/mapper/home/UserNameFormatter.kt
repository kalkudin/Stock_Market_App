package com.example.finalproject.presentation.mapper.home

import com.example.finalproject.domain.model.user_initials.GetUserInitials
import java.util.Locale

fun GetUserInitials.formatFirstName() : String {
    return "Hey ${firstName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }}"
}