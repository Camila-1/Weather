package com.example.weather

import okhttp3.Interceptor
import okhttp3.Response

class MyIntercwptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().url(
                chain.request().url().newBuilder()
                    .addQueryParameter("appid", "2ba37aa25a1076c15ae993cb5de372d6")
                    .build()
            ).build()
        )
    }
}