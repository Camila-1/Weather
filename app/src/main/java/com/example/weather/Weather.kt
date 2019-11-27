package com.example.weather

data class Weather(val tepm: Double,
                   val temp_min: Double,
                   val temp_max: Double,
                   val pressure: Double,
                   val humidity: Double,
                   val wind: Double)