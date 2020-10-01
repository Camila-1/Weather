package com.example.weather.city_management

import androidx.lifecycle.ViewModel
import arrow.core.Either
import arrow.core.Nel
import com.example.weather.db.City
import com.example.weather.livedata.AddCityButtonAction
import com.example.weather.livedata.SingleLiveEvent
import com.example.weather.network.google_api.response.Variant
import com.example.weather.network.weather_api.response.WeatherResponse
import com.example.weather.utils.Weather
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import java.util.*
import javax.inject.Inject

class CitiesManagementViewModel @Inject constructor(private val repository: ManagementRepository) : ViewModel() {

    var listWeatherData: WeatherResponse? = null

    val addCityButtonAction = SingleLiveEvent<AddCityButtonAction>()

    val cities = repository.cities

    lateinit var citiesWithWeather: Nel<Pair<Variant, Optional<Weather>>>

    fun addCity(city: City) {
        GlobalScope.launch(Dispatchers.IO) { repository.citiesDao.insert(city) }
    }

    suspend fun findCitiesWeather(string: String): Either<CityManagementError, Nel<Pair<Variant, Optional<Weather>>>> {
        return withContext(coroutineContext + Dispatchers.IO) {
            repository.autocompleteCitiesWeather(string)
        }
    }

    fun updateSessionToken() = repository.updateSessionToken()

    fun handleButtonClick(pair: Pair<Variant, Optional<Weather>>, position: Int) {
        when(pair.first.isAdded) {
            true -> { addCityButtonAction.value = AddCityButtonAction.OpenDetails }
            false -> {
                pair.first.let {
                    addCity(City(it.placeId, it.terms[0].value))
                    citiesWithWeather[position].first.isAdded = true
                    addCityButtonAction.value = AddCityButtonAction.AddCity(citiesWithWeather)
                }
            }
        }
    }
}
