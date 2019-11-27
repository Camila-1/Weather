package com.example.weather

import retrofit2.Retrofit

object ServiceBuilder {
    val BASE_URL = "https://samples.openweathermap.org/data/2.5/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .build()

    fun weatherService(): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }
}