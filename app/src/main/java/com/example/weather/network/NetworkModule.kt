package com.example.weather.network

import android.content.Context
import com.example.weather.LocationProvider
import com.example.weather.network.google_api.GoogleApiModule
import com.example.weather.network.weather_api.WeatherApiModule
import com.google.android.gms.location.FusedLocationProviderClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides

@Module(includes = [WeatherApiModule::class, GoogleApiModule::class])
class NetworkModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
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
