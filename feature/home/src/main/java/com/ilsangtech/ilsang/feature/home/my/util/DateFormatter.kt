package com.ilsangtech.ilsang.feature.home.my.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(input: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

    val date = inputFormat.parse(input)
    return outputFormat.format(date!!)
}