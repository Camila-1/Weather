package com.example.weather.network.google_api

import com.example.weather.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class GooglePlacesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().url(
                chain.request().url.newBuilder()
                    .addQueryParameter("key", BuildConfig.GOOGLE_API_KEY)
                    .build()
            ).build()
        )
    }
}
