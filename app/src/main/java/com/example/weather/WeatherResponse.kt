package com.example.weather

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val id: Long,
    val main: String,
    val weather: List<Weather>,
    val wind: String,
    val rain: Map<String, Int>?,
    val clouds: Map<String, Int>?
)