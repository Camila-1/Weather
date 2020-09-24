package com.example.weather.di

import com.example.weather.city_management.CitiesManagementRepository
import com.example.weather.city_management.ManagementRepository
import com.example.weather.db.CitiesDao
import com.example.weather.network.google_api.GoogleApiService
import com.example.weather.network.weather_api.WeatherApiService
import com.example.weather.settings.SharedPreferenceHolder
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideCitiesRepository(
        citiesDao: CitiesDao,
        googleApiService: GoogleApiService,
        weatherApiService: WeatherApiService,
        spHolder: SharedPreferenceHolder
    ): ManagementRepository {
        return CitiesManagementRepository(citiesDao, googleApiService, weatherApiService, spHolder)
    }
}