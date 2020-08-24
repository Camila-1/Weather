package com.example.weather.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City (
    val id: Int,
    val name: String
): Parcelable