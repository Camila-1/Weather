package com.example.weather.network.google_api.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Predictions(
    val status: String,
    val predictions: List<Variant>
)