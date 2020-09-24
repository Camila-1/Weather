package com.example.weather.city_management

import androidx.lifecycle.ViewModel
import arrow.core.Either
import arrow.core.Nel
import com.example.weather.db.City
import com.example.weather.network.google_api.response.Variant
import com.example.weather.network.weather_api.response.WeatherResponse
import com.example.weather.utils.Weather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

class CitiesManagementViewModel @Inject constructor(private val repository: ManagementRepository) : ViewModel() {

    var listWeatherData: WeatherResponse? = null

    val cities = repository.cities

    fun addCity(city: City) {
        runBlocking { repository.citiesDao.insert(city) }
    }

    suspend fun findCitiesWeather(string: String): Either<CityManagementError, Nel<Pair<Variant, Optional<Weather>>>> {
        return repository.autocompleteCitiesWeather(string)
    }

}