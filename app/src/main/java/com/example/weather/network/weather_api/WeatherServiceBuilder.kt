package com.example.weather.network.weather_api

import retrofit2.Retrofit

class WeatherServiceBuilder (
    private val retrofit: Retrofit,
    private val weatherService: Class<WeatherApiService>
) {
    fun weatherService(): WeatherApiService = retrofit.create(weatherService)
}
