package com.example.weather.network.google_api.response

import com.squareup.moshi.Json

data class StructuredFormatting(
    @Json(name = "main_text") val mainText: String,
    @Json(name = "secondary_text") val secondaryText: String
)