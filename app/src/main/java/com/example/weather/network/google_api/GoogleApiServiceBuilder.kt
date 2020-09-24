package com.example.weather.network.google_api

import retrofit2.Retrofit

class GoogleApiServiceBuilder(
    private val retrofit: Retrofit,
    private val googleService: Class<GoogleApiService>
) {
    fun googleService() = retrofit.create(googleService)
}
