package com.ilsangtech.ilsang.core.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateConverter {
    fun timeAgoSinceDate(dateString: String, withYear: Boolean = true): String {
        val datePart = dateString.split(".").firstOrNull() ?: return ""

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date: Date = try {
            inputFormat.parse(datePart) ?: return ""
        } catch (e: Exception) {
            return ""
        }

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)

        calendar.time = date
        val dateYear = calendar.get(Calendar.YEAR)

        val outputFormat = when {
            withYear && currentYear != dateYear -> SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault())
            else -> SimpleDateFormat("M월 d일", Locale.getDefault())
        }
        return outputFormat.format(date)
    }

    fun formatDate(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

        val date = inputFormat.parse(input)
        return outputFormat.format(date!!)
    }
}