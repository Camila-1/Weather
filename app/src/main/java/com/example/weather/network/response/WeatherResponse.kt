package com.example.weather.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class WeatherResponse (
    val city: City,
    val list: List<WeatherData>
): Parcelable