package com.example.weather

import retrofit2.Retrofit

object ServiceBuilder {
    fun weatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }
}