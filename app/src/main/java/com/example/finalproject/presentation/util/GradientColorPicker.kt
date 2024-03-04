package com.example.finalproject.presentation.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import kotlin.random.Random

fun View.setCustomGradientBackground() {
    //this needs to be adjusted more to display certain colors.
    val primaryColors = listOf(
        Triple(215, 79, 96),
        Triple(79, 160, 215),
        Triple(79, 215, 180),
        Triple(92, 225, 124),
        Triple(225, 142, 92),
        Triple(225, 92, 164)
    )

    val (r1, g1, b1) = primaryColors[Random.nextInt(primaryColors.size)]

    val sortedColors = listOf(r1, g1, b1).sortedDescending()
    val primary = sortedColors[0]
    val secondary = sortedColors[1]

    val r2 = adjustColorComponent(r1, primary, secondary, -40, 20)
    val g2 = adjustColorComponent(g1, primary, secondary, -40, 20)
    val b2 = adjustColorComponent(b1, primary, secondary, -40, 20)

    val gradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT,
        intArrayOf(Color.rgb(r1, g1, b1), Color.rgb(r2, g2, b2))
    ).apply {
        cornerRadius = 20f
    }

    this.background = gradientDrawable
}

private fun adjustColorComponent(component: Int, primary: Int, secondary: Int, primaryAdjustment: Int, secondaryAdjustment: Int): Int {
    return when (component) {
        primary -> (component + primaryAdjustment).coerceIn(0, 255)
        secondary -> (component + secondaryAdjustment).coerceIn(0, 255)
        else -> component
    }
}