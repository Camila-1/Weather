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

    fun isGeolocationEnabled(context: Context): Boolean {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        return preference.getBoolean("coordinates", true)
    }

    fun getUnit(context: Context): String {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        return preference.getString("units", "").toString()
    }

    fun getLang(context: Context): String {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        return preference.getString("lang", "").toString()
    }

    fun getCity(context: Context): String {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        return preference.getString("city", "")!!
    }
}