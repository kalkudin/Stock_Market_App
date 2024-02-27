package com.example.finalproject.presentation.stock_feature.company_details.screen

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateValueFormatter : ValueFormatter() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return dateFormat.format(Date(value.toLong()))
    }
}