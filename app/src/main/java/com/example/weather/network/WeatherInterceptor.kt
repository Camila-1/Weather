package com.example.weather.network

import com.example.weather.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class WeatherInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().url(
                chain.request().url.newBuilder()
                    .addQueryParameter("appid", BuildConfig.OPEN_WEATHER_KEY)
                    .build()
            ).build()
        )
    }
}