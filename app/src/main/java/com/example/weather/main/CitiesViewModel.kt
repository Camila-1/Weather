package com.example.weather.main

import androidx.lifecycle.ViewModel
import com.example.weather.network.response.WeatherData
import com.example.weather.network.response.WeatherResponse
import javax.inject.Inject

class CitiesViewModel @Inject constructor(repository: CitiesRepository) : ViewModel() {

    var listWeatherData: WeatherResponse? = null

    val cities = repository.cities



}