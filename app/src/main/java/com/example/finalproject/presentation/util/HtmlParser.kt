package com.example.finalproject.presentation.util

import org.jsoup.Jsoup

fun parseHtmlContent(htmlContent: String) {
    val document = Jsoup.parse(htmlContent)
    val paragraphs = document.select("p")

    paragraphs.forEach { paragraph ->
        println(paragraph.text())
    }
}