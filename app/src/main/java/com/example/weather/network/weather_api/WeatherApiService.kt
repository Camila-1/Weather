package com.example.weather.network.weather_api

import com.example.weather.network.weather_api.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getCurrentWeatherDataByCityName(
        @Query("q") city: String,
        @Query("units") unit: String,
        @Query("lang") lang: String
    ): WeatherResponse

    @GET("forecast")
    suspend fun getCurrentWeatherDataByCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") unit: String,
        @Query("lang") lang: String
    ): WeatherResponse
}
