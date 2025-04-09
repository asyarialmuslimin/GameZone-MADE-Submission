package com.saifur.gamezone.core.utils

import java.text.SimpleDateFormat
import java.util.Locale

object StringUtil {
    fun convertDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("MMMM, dd yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(dateString.split("T").first())
            date?.let { outputFormat.format(it) } ?: "-"
        } catch (e: Exception) {
            "-"
        }
    }
}