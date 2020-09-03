package com.example.weather.settings

import android.content.Context
import androidx.preference.PreferenceManager
import javax.inject.Inject

class SharedPreferenceHolder @Inject constructor(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val preferenceEditor = preferences.edit()

    val getTemperatureUnit = preferences.getString("units", "")
        get() {
            return when (field) {
                "metric" -> " °C"
                "imperial" -> " °F"
                else -> ""
            }
        }
    val getDistanceUnit = preferences.getString("units", "")
        get() {
            return when (field) {
                "metric" -> " М/с"
                "imperial" -> " Миль/час"
                else -> ""
            }
        }

    var isGeolocationEnabled = preferences.getBoolean("coordinates", true)
        set(value) = preferenceEditor.putBoolean("coordinates", value).apply()

    val getUnit = preferences.getString("units", "").toString()

    val getLang = preferences.getString("lang", "").toString()

    val getCity = preferences.getString("city", "")

    var coordinates = mapOf("lon" to preferences.getString("lon", ""),
        "lat" to preferences.getString("lat", ""))
        set(value) {
            field = value
            preferenceEditor.putString("lon", value.get("lon"))
                .putString("lat", value.get("lat"))
                .apply()
        }
}