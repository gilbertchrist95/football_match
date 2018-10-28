package com.treblig.footballmatch.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTime {
     fun convertDate(date: String): String {
        var result = ""
        val format = "EEE, dd MMM yyyy"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        try {
            val date: Date = dateFormat.parse(date)
            val newFormat = SimpleDateFormat(format, Locale.ENGLISH)
            result = newFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return result
    }

    @SuppressLint("SimpleDateFormat")
    fun toSimpleString(date: Date?):String? = with(date?: Date()){
        SimpleDateFormat("EEE, dd MM yyyy").format(this)
    }
}