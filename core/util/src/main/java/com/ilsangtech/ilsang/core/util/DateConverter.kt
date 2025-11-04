package com.ilsangtech.ilsang.core.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateConverter {
    private const val DEFAULT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"

    fun formatDate(input: String, outputPattern: String = "yyyy.MM.dd"): String {
        return try {
            val inputFormat = SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).apply {
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

    fun isAfterDays(input: String?, days: Int = 1): Boolean {
        if (input.isNullOrBlank()) return true
        return try {
            val sdf = SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("Asia/Seoul")
            }
            val date = sdf.parse(input) ?: return true
            val now = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).time
            val diff = now.time - date.time
            diff >= days * 24 * 60 * 60 * 1000L
        } catch (_: Exception) {
            true
        }
    }

    fun nowString(): String {
        val sdf = SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return sdf.format(Date())
    }

    fun getRemainHours(
        date: String,
        day: Int? = null,
        week: Int? = null,
        month: Int? = null
    ): Int {
        val sdf = SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        val endDate = Calendar.getInstance().apply {
            time = sdf.parse(date) ?: return -1
            add(Calendar.DAY_OF_MONTH, day ?: 0)
            add(Calendar.WEEK_OF_MONTH, week ?: 0)
            add(Calendar.MONTH, month ?: 0)
        }.time

        val now = Calendar.getInstance(Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }.time

        val remainTime = endDate.time - now.time
        if (remainTime <= 0) return 0
        val remainHour =
            remainTime / (60 * 60 * 1000) + if (remainTime % (60 * 60 * 1000) != 0L) 1 else 0

        return remainHour.toInt()
    }
}