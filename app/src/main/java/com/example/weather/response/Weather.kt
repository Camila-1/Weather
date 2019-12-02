package com.example.weather.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
) : Parcelable