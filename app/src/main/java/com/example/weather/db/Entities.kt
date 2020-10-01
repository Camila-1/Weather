package com.example.weather.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City(
    @PrimaryKey val id: String,
    val name: String
)

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey val id: Int,
    val city_id: Int,
    val date_time: String,
    val clouds: Double,
    val humidity: Double,
    val pressure: Double,
    val temperature: Double,
    val temperature_max: Double,
    val temperature_min: Double,
    val rain: Double,
    val snow: Double,
    val description: String,
    val icon: Int,
    val wind_speed: Double,
    val wind_degree: Double
)
