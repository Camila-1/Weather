package com.example.weather.network.weather_api.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class WeatherData (
    @Json(name = "dt") val dateTime: Long,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val rain: Map<String, Double>?,
    val snow: Map<String, Double>?,
    val clouds: Map<String, Double>?
)