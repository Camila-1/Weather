package com.example.weather.network.weather_api.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class WeatherResponse (
    val city: City,
    val list: List<WeatherData>
) {
    fun presentDay() {
        list.forEach {
            it.dateTime
        }
    }
}