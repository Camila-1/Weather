package com.example.weather.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class WeatherData (
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val rain: Map<String, Double>?,
    val snow: Map<String, Double>?,
    val clouds: Map<String, Double>?,
    @Json(name = "dt_txt") val dateTime: String
) : Parcelable