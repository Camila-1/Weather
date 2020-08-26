package com.example.weather.main

import androidx.lifecycle.ViewModel
import com.example.weather.network.response.WeatherData
import com.example.weather.network.response.WeatherResponse

class CitiesViewModel(repository: CitiesRepository) : ViewModel() {

    var listWeatherData: WeatherResponse? = null

    private var checkedItem: WeatherData? = null




}