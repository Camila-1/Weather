package com.example.weather.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventViewModel : ViewModel() {

    enum class State {
        CITY_MANAGEMENT_FRAGMENT,
        CITY_WEATHER_FRAGMENT,
        SETTINGS,
        SEARCH_CITY
    }

    var state = MutableLiveData<State>(State.CITY_WEATHER_FRAGMENT)
}