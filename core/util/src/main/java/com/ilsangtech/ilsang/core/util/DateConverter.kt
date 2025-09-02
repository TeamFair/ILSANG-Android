package com.ilsangtech.ilsang.core.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateConverter {
    fun formatDate(input: String, outputPattern: String = "yyyy.MM.dd"): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("Asia/Seoul")
            }
            val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("Asia/Seoul")
            }
            val date = inputFormat.parse(input)
            outputFormat.format(date!!)
        } catch (_: Exception) {
            "알 수 없음"
        }
    }
}