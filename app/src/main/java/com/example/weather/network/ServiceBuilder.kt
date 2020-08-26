package com.example.weather.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ServiceBuilder(val context: Context) {
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(WeatherInterceptor())
        .addInterceptor(ChuckerInterceptor(context))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun weatherService(): WeatherService = retrofit.create(WeatherService::class.java)

}
