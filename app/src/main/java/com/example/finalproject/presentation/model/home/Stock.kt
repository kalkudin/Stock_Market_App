package com.example.finalproject.presentation.model.home

data class Stock(
    val symbol : String,
    val name : String,
    val change : String,
    val changeValue : Double,
    val price : String,
    val changesPercentage : String,
    val performingType : PerformingType
) {
    enum class PerformingType {
        BEST_PERFORMING,
        WORST_PERFORMING,
        ACTIVE_PERFORMING,
    }
}