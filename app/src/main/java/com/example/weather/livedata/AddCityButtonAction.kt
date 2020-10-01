package com.example.weather.livedata

import arrow.core.Nel
import com.example.weather.network.google_api.response.Variant
import com.example.weather.utils.Weather
import java.util.*

sealed class AddCityButtonAction {
    class AddCity(val updatedCitiesList: Nel<Pair<Variant, Optional<Weather>>>) : AddCityButtonAction()
    object OpenDetails : AddCityButtonAction()
}