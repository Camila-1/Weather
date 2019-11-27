package com.example.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("weather?q={city name}")
    fun getCurrentWeatherDataByCityName(@Path("city name") city: String): Call<List<WeatherResponse>>

    @GET("weather?lat={lat}&lon={lon}")
    fun getCurrentWeatherDataByCoordinates(@Path("lat") lat: String, @Path("lon") lon: String): Call<List<WeatherResponse>>
}