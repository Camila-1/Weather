package com.example.weather.city_management

import androidx.lifecycle.LiveData
import arrow.core.Either
import arrow.core.Nel
import com.example.weather.db.CitiesDao
import com.example.weather.db.City
import com.example.weather.network.google_api.response.Variant
import com.example.weather.network.weather_api.response.WeatherResponse
import com.example.weather.utils.Weather
import java.util.*

interface ManagementRepository {

    val cities: LiveData<List<City>>

    val citiesDao: CitiesDao

    suspend fun autocompleteCitiesWeather(string: String): Either<CityManagementError, Nel<Pair<Variant, Optional<Weather>>>>

    fun updateSessionToken()
}

sealed class CityManagementError

object CityNotFoundError: CityManagementError()

object WeatherApiError: CityManagementError()
