package com.example.finalproject.presentation.util

import android.view.View
import androidx.core.content.ContextCompat
import com.example.finalproject.R
import com.example.finalproject.presentation.model.home.Stock

fun setBackground(type: Stock.PerformingType, view: View) {
    val drawableResId = when (type) {
        Stock.PerformingType.BEST_PERFORMING -> R.drawable.style_stocks_to_watch_best_gradient
        Stock.PerformingType.WORST_PERFORMING -> R.drawable.style_stocks_to_watch_worst_gradient
        Stock.PerformingType.ACTIVE_PERFORMING -> R.drawable.style_stocks_to_watch_active_gradient
    }
    view.background = ContextCompat.getDrawable(view.context, drawableResId)
}