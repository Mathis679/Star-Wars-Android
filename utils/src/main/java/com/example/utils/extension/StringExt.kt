package com.example.utils.extension

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun String?.safe(): String {
    return this ?: ""
}

fun String.toDate(): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd")
    return try {
        format.parse(this)
    } catch (e: Exception){
        e.printStackTrace()
        null
    }
}

fun String.extractUrlId(): Int? {
    val segments = this.split('/')
    segments.forEach {
        it.toIntOrNull()?.let {
            return it
        }
    }
    return null
}