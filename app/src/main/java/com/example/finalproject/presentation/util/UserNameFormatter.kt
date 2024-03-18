package com.example.finalproject.presentation.util

import com.example.finalproject.domain.model.UserInitials
import java.util.Locale

fun UserInitials.formatName() : String {
    return firstName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } +
            " " +
            lastName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}