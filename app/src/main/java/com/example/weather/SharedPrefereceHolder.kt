package com.example.weather

import android.content.Context
import androidx.preference.PreferenceManager

object SharedPreferenceHolder {

    fun getTemperatureUnit(context: Context): String {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        return when (preference.getString("units", "")) {
            "metric" -> " °C"
            "imperial" -> " °F"
            else -> ""
        }
    }

    fun getDistanceUnit(context: Context): String {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        return when (preference.getString("units", "")) {
            "metric" -> " М/с"
            "imperial" -> " Миль/час"
            else -> ""
        }
    }
}