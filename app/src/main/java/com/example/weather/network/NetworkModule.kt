package com.example.weather.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.weather.LocationProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideOkHttpClient(context: Context, interceptor: WeatherInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(ChuckerInterceptor(context))
            .build()
    }

    @Provides
    fun provideInterceptor(): WeatherInterceptor {
        return WeatherInterceptor()
    }

    @Provides
    fun provideMoshi(): Moshi? {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideWeatherService(): Class<WeatherService> {
        return WeatherService::class.java
    }

    @Provides
    fun provideServiceBuilder(retrofit: Retrofit, weatherService: Class<WeatherService>): ServiceBuilder {
        return ServiceBuilder(retrofit, weatherService)
    }

    @Provides
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
        return FusedLocationProviderClient(context)
    }

    @Provides
    fun provideLocationProvider(
        context: Context,
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationProvider {
        return LocationProvider(context, fusedLocationProviderClient)
    }

}