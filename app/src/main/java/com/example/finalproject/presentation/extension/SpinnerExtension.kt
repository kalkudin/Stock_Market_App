package com.example.finalproject.presentation.extension

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

fun Spinner.setOnItemSelected(onItemSelected: (String) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            val selectedItem = parent.getItemAtPosition(position).toString()
            onItemSelected(selectedItem)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }
}