package com.example.weather.main

import androidx.lifecycle.ViewModel
import com.example.weather.db.City
import com.example.weather.network.response.WeatherResponse
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CitiesViewModel @Inject constructor(private val repository: CitiesRepository) : ViewModel() {

    var listWeatherData: WeatherResponse? = null

    val cities = repository.cities

    fun addCity(city: City) {
        runBlocking { repository.citiesDao.insert(city) }
    }


}