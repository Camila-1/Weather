package com.example.weather.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {

    enum class Screen {
        CITY_MANAGEMENT,
        CITY_WEATHER,
        SETTINGS,
        SEARCH_CITY,
        DETAILS
    }

    var state = MutableLiveData(Screen.CITY_WEATHER)

    fun navigateToCityManagement() {
        state.value = Screen.CITY_MANAGEMENT
    }

    fun navigateToSearchCity() {
        state.value = Screen.SEARCH_CITY
    }

    fun navigateToCityWeather() {
        state.value = Screen.CITY_WEATHER
    }

    fun navigateToCityDetails() {
        state.value = Screen.DETAILS
    }

    fun navigateToCitySettings() {
        state.value = Screen.SETTINGS
    }
}