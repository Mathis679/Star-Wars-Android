package com.example.utils

import java.text.SimpleDateFormat
import java.util.Date

class DateUtil {
    companion object {
        fun displayDate(date: Date): String {
            val sdf = SimpleDateFormat("dd / MM / yyyy")
            return sdf.format(date)
        }
    }
}