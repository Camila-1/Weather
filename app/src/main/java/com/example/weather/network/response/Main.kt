package com.example.weather.network.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Main(
    val temp: Double,
    val humidity: Double,
    val pressure: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double
) : Parcelable