package com.example.weather.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse (
    val city: City,
    val list: List<WeatherData>
)