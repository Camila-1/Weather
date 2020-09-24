package com.example.weather.network.google_api

import com.example.weather.network.google_api.response.Predictions
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApiService {

    @GET("json")
    suspend fun findCities(
        @Query("language") language: String,
        @Query("types") types: String,
        @Query("input") input: String,
        @Query("sessiontoken") token: String
    ): Predictions
}
