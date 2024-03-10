package com.example.finalproject.presentation.util

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateValueFormatter : ValueFormatter() {
    private val dateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return dateFormat.format(Date(value.toLong()))
    }
}