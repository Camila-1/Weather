package com.example.weather.network.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherData (
    @Json(name = "dt") val dateTime: Long,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val rain: Map<String, Double>?,
    val snow: Map<String, Double>?,
    val clouds: Map<String, Double>?
) : Parcelable