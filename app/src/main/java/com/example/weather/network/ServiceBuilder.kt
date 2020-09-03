package com.example.weather.network

import retrofit2.Retrofit
import javax.inject.Inject

class ServiceBuilder (
    private val retrofit: Retrofit,
    private val weatherService: Class<WeatherService>
) {
    fun weatherService(): WeatherService = retrofit.create(weatherService)
}
