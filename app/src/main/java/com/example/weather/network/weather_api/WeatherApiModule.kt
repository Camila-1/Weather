package com.example.weather.network.weather_api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class WeatherApiModule {

    @Singleton
    @Provides
    @Named("weather_api")
    fun provideRetrofit(@Named("weather_api") okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Named("weather_api")
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(WeatherInterceptor())
            .addInterceptor(ChuckerInterceptor(context))
            .build()
    }

    @Provides
    fun provideWeatherServiceBuilder(@Named("weather_api") retrofit: Retrofit): WeatherApiService {
        return WeatherServiceBuilder(retrofit, WeatherApiService::class.java).weatherService()
    }
}