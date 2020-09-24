package com.example.weather.network.weather_api.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Weather (
    val description: String,
    val icon: String
)