package com.example.myapplication.utils

import java.text.ParseException
import java.text.SimpleDateFormat

object DateUtils {
    const val PATTERN_DATE_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    const val PATTERN_DATE_SERVER_SHORT = "yyyy-MM-dd'T'HH:mm:ss"
    const val PATTERN_DATE_VIEW = "dd/MM/yyyy"
    const val DATE_DEFECT = "2000-01-01T12:00:00.000"

    fun formatDateToView(date: String): String {
        return try {
            val simpleDateFormatServer = SimpleDateFormat(PATTERN_DATE_SERVER)
            val dateTime = simpleDateFormatServer.parse(date)
            SimpleDateFormat(PATTERN_DATE_VIEW).format(dateTime)
        } catch (e: ParseException) {
            val simpleDateFormatServer = SimpleDateFormat(PATTERN_DATE_SERVER_SHORT)
            val dateTime = simpleDateFormatServer.parse(date)
            SimpleDateFormat(PATTERN_DATE_VIEW).format(dateTime)
        }
    }

    fun formatDateToServer(date: String): String {
        return try {
            val simpleDateFormatServer = SimpleDateFormat(PATTERN_DATE_VIEW)
            val dateTime = simpleDateFormatServer.parse(date)
            SimpleDateFormat(PATTERN_DATE_SERVER).format(dateTime)
        } catch (e: ParseException) {
            DATE_DEFECT
        }
    }
}