package com.example.weather

import com.example.weather.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast")
    fun getCurrentWeatherDataByCityName(@Query("q") city: String): Call<WeatherResponse>

    @GET("weather?lat={lat}&lon={lon}")
    fun getCurrentWeatherDataByCoordinates(@Path("lat") lat: String, @Path("lon") lon: String): Call<List<WeatherResponse>>
}