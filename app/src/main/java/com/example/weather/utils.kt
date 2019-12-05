package com.example.weather

import android.content.Context
import androidx.preference.PreferenceManager
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(unixTime: Long, context: Context): String {
    val dateFormat = SimpleDateFormat("d MMMM hh:ss", Locale(PreferenceManager.getDefaultSharedPreferences(context).getString("lang", "")!!))
    return dateFormat.format(unixTime * 1000L)
}