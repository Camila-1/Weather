package com.example.weather.response

import com.squareup.moshi.Json

data class City (
    val id: Int,
    val name: String,
    @Json(name = "coord")
    val coordinates: Map<String, Double>,
    val country: String
)