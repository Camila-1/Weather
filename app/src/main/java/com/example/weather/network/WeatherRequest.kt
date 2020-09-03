package com.example.weather.network

import android.content.Context
import com.example.weather.LocationProvider
import com.example.weather.settings.SharedPreferenceHolder
import com.example.weather.network.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

class WeatherRequest @Inject constructor(
    private val serviceBuilder: ServiceBuilder,
    private val spHolder: SharedPreferenceHolder
) {
    private val weatherService = serviceBuilder.weatherService()

    suspend fun getWeatherResponseData(): WeatherResponse? {
        return withContext(Dispatchers.IO) {
            getWeatherResponse().execute().body()
        }
    }



    fun getWeatherResponse(): Call<WeatherResponse> {
        val unit = spHolder.getUnit
        val lang = spHolder.getLang


        return if (spHolder.isGeolocationEnabled) {
            val coordinates = spHolder.coordinates
            weatherService.getCurrentWeatherDataByCoordinates(coordinates.getValue("lat")!!,
                coordinates.getValue("lon")!!, unit, lang)

        } else weatherService.getCurrentWeatherDataByCityName(spHolder.getCity!!, unit, lang)
    }
}