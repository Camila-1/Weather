package com.example.weather

import com.example.weather.network.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast")
    fun getCurrentWeatherDataByCityName(@Query("q") city: String, @Query("units") unit: String, @Query("lang") lang: String): Call<WeatherResponse>

    @GET("forecast")
    fun getCurrentWeatherDataByCoordinates(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") unit: String, @Query("lang") lang: String): Call<WeatherResponse>
}