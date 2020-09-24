package com.example.weather.utils

import android.content.Context
import androidx.preference.PreferenceManager
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(unixTime: Long, locale: Locale): String {
    val dateFormat = SimpleDateFormat("d MMMM hh:ss", locale)
    return dateFormat.format(unixTime * 1000L)
}

fun getLayoutIdentifier(name: String, context: Context): Int {
    return context.resources.getIdentifier(name, "layout", context.packageName)
}

fun getDrawableIdentifier(name: String, context: Context): Int {
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}