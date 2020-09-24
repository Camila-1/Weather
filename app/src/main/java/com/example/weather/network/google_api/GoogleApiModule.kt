package com.example.weather.network.google_api

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
class GoogleApiModule {

    @Singleton
    @Provides
    @Named("google_api")
    fun provideRetrofit(
        @Named("google_api") okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Named("google_api")
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(GooglePlacesInterceptor())
            .addInterceptor(ChuckerInterceptor(context))
            .build()
    }

    @Provides
    fun provideGoogleApiService(
        @Named("google_api") retrofit: Retrofit): GoogleApiService {
        return GoogleApiServiceBuilder(retrofit, GoogleApiService::class.java).googleService()
    }
}