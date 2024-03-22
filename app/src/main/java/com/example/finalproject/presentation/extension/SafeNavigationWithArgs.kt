package com.example.finalproject.presentation.extension

import android.os.Bundle
import androidx.navigation.NavController

fun NavController.safeNavigateWithArguments(actionId:Int, bundle: Bundle): Boolean {
    return currentDestination?.getAction(actionId)?.let {
        navigate(actionId, bundle)
        true
    } ?: false
}